package com.megacity.server.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.megacity.server.Model.Cabs;
import com.megacity.server.Model.Category;
import com.megacity.server.Repository.CabsRepository;
import com.megacity.server.Repository.CategoryRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class CabsServiceImpl implements CabsService {

    @Autowired
    private CabsRepository cabsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EmailService emailService;


    @Autowired
    private JavaMailSender mailSender;

    @Override
    public Cabs addCab(Cabs cab) {
        return cabsRepository.save(cab);
    }

    @Override
    public void deleteCab(String cabId) {
        // Fetch the cab details before deleting
        Cabs cab = cabsRepository.findById(cabId)
            .orElseThrow(() -> new RuntimeException("Cab not found with ID: " + cabId));

        // Delete the cab
        cabsRepository.deleteById(cabId);

        // Send an email notification
        sendCabDeletionEmail(cab);
    }

 @Override
    public List<Map<String, Object>> getAllCabs() {
        List<Cabs> cabsList = cabsRepository.findAll();
        List<Map<String, Object>> responseList = new ArrayList<>();

        for (Cabs cab : cabsList) {
            Map<String, Object> cabResponse = new HashMap<>();
            cabResponse.put("cabId", cab.getCabId());
            cabResponse.put("cabModel", cab.getCabModel());
            cabResponse.put("cabNumberPlate", cab.getCabNumberPlate());
            cabResponse.put("seats", cab.getSeats());
            cabResponse.put("imgUrl", cab.getImgUrl());
            cabResponse.put("availabilityStatus", cab.getAvailabilityStatus());
            cabResponse.put("hasAC", cab.getHasAC());
            cabResponse.put("luggageCapacity", cab.getLuggageCapacity());
            cabResponse.put("driverName", cab.getDriverName());
            cabResponse.put("driveremail", cab.getDriveremail());
            cabResponse.put("contactNumber", cab.getContactNumber());
            cabResponse.put("location", cab.getLocation());

            // Fetch category name using categoryId
            Optional<Category> category = categoryRepository.findById(cab.getCategoryId());
            cabResponse.put("categoryName", category.map(Category::getCategoryName).orElse("Unknown"));

            Optional<Category> price = categoryRepository.findById(cab.getCategoryId());
            cabResponse.put("pricePerKm", price.map(Category::getPricePerKm).orElse(0.0));

            responseList.add(cabResponse);
        }
        return responseList;
    }


    @Override
    public Cabs getCabById(String cabId) {
        return cabsRepository.findById(cabId)
        .orElseThrow(() -> new RuntimeException("Cab not found with ID: " + cabId));
    }

    @Override
    public Cabs updateCab(String cabId, Cabs cab) {
        Optional<Cabs> existingCab = cabsRepository.findById(cabId);
        if (existingCab.isPresent()) {
            Cabs cabToUpdate = existingCab.get();
            cabToUpdate.setAvailabilityStatus(cab.getAvailabilityStatus());

            if ("Available".equalsIgnoreCase(cab.getAvailabilityStatus())) {
                String driverEmail = cabToUpdate.getDriveremail();
                String userName = cabToUpdate.getUserName();
                String password = cabToUpdate.getPassword(); 

                if (driverEmail != null && !driverEmail.isEmpty()) {
                    System.out.println("Sending email to: " + driverEmail);
                    emailService.sendApprovalEmail(driverEmail, userName, password);
                } else {
                    System.out.println("Driver email is missing, email not sent.");
                }
            }

            return cabsRepository.save(cabToUpdate);
        }
        throw new RuntimeException("Cab not found with ID: " + cabId);
    }


    @Override
    public List<Cabs> getcabsById(String categoryId) {
        return cabsRepository.findByCategoryId(categoryId);
    }




    private void sendCabDeletionEmail(Cabs cab) {
        String to = cab.getDriveremail(); // Get the driver's email
        String subject = "Cab Deletion Notification - Megacity Cab Services";

        // HTML Content for the Deletion Email
        String htmlContent = "<html>"
            + "<body style='font-family: Arial, sans-serif; color: #333;'>"
            + "<div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;'>"
            + "<h2 style='color: #007BFF;'>Cab Deletion Notification</h2>"
            + "<p>Dear " + cab.getDriverName() + ",</p>"
            + "<p>We regret to inform you that your cab with the following details proposal rejected  from our system:</p>"
            + "<h3 style='color: #007BFF;'>Cab Details:</h3>"
            + "<ul>"
            + "<li><strong>Cab ID:</strong> " + cab.getCabId() + "</li>"
            + "<li><strong>Cab Model:</strong> " + cab.getCabModel() + "</li>"
            + "<li><strong>Plate Number:</strong> " + cab.getCabNumberPlate() + "</li>"
            + "<li><strong>Driver Name:</strong> " + cab.getDriverName() + "</li>"
            + "<li><strong>Driver Email:</strong> " + cab.getDriveremail() + "</li>"
            + "</ul>"
            + "<h3 style='color: #007BFF;'>Important Notes:</h3>"
            + "<ul>"
            + "<li>If you believe this deletion was a mistake, please contact our support team immediately.</li>"
            + "<li>You can reach us at <a href='mailto:megacity@gmail.com'>megacity@gmail.com</a> or call us at +94 764 610 843.</li>"
            + "</ul>"
            + "<p>Thank you for being a part of Megacity Cab Services. We hope to serve you again in the future.</p>"
            + "<p>Best Regards,</p>"
            + "<p><strong>The Megacity Cab Services Team</strong></p>"
            + "<p>Email: <a href='mailto:megacity@gmail.com'>megacity@gmail.com</a></p>"
            + "<p>Phone: +94 764 610 843</p>"
            + "</div>"
            + "</body>"
            + "</html>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("onethsayakkara@gmail.com"); // Set the "from" address explicitly
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // Set the email content as HTML

            mailSender.send(message);
            System.out.println("Cab deletion email successfully sent to: " + to);
        } catch (MessagingException e) {
            System.err.println("Error while sending cab deletion email: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
