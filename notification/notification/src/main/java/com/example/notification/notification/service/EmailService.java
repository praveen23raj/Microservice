package com.example.notification.notification.service;

import com.example.notification.notification.dto.BookingConfirmedEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendBookingEmail(BookingConfirmedEvent event) {

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setTo(event.getUserEmail());

            helper.setSubject("Flight Booking Confirmation ✈️");

            String htmlContent =
                    """
                    <html>
                    <body style="
                        font-family: Arial, sans-serif;
                        background-color: #f4f6f8;
                        padding: 20px;
                    ">

                        <div style="
                            max-width: 600px;
                            margin: auto;
                            background-color: white;
                            border-radius: 12px;
                            padding: 30px;
                            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                        ">

                            <div style="text-align: center;">
                                <h1 style="color: #1e88e5; margin-bottom: 5px;">
                                    ✈️ Flight Booking Confirmed
                                </h1>

                                <p style="color: #666; font-size: 15px;">
                                    Your booking has been successfully confirmed
                                </p>
                            </div>

                            <hr style="border: none; border-top: 1px solid #eee; margin: 25px 0;">

                            <table style="
                                width: 100%;
                                border-collapse: collapse;
                                font-size: 15px;
                            ">

                                <tr>
                                    <td style="padding: 12px; font-weight: bold;">
                                        Booking ID
                                    </td>

                                    <td style="padding: 12px;">
                                        """ + event.getBookingId() + """
                                    </td>
                                </tr>

                                <tr style="background-color: #f8f9fa;">
                                    <td style="padding: 12px; font-weight: bold;">
                                        Flight Number
                                    </td>

                                    <td style="padding: 12px;">
                                        """ + event.getFlightNumber() + """
                                    </td>
                                </tr>

                                <tr>
                                    <td style="padding: 12px; font-weight: bold;">
                                        Departure
                                    </td>

                                    <td style="padding: 12px;">
                                        """ + event.getOrigin() + """
                                    </td>
                                </tr>

                                <tr style="background-color: #f8f9fa;">
                                    <td style="padding: 12px; font-weight: bold;">
                                        Destination
                                    </td>

                                    <td style="padding: 12px;">
                                        """ + event.getDestination() + """
                                    </td>
                                </tr>

                                <tr>
                                    <td style="padding: 12px; font-weight: bold;">
                                        Seats Booked
                                    </td>

                                    <td style="padding: 12px;">
                                        """ + event.getNumberOfSeats() + """
                                    </td>
                                </tr>

                            </table>

                            <div style="
                                margin-top: 30px;
                                padding: 20px;
                                background-color: #f8f9fa;
                                border-radius: 10px;
                                text-align: center;
                            ">

                                <p style="
                                    margin: 0;
                                    color: #555;
                                    font-size: 14px;
                                ">
                                    Thank you for choosing SkyWay Airlines.
                                </p>

                                <p style="
                                    margin-top: 10px;
                                    color: #888;
                                    font-size: 13px;
                                ">
                                    We wish you a safe and pleasant journey ✈️
                                </p>

                            </div>

                        </div>

                    </body>
                    </html>
                    """;

            helper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (MessagingException e) {

            throw new RuntimeException(
                    "Failed to send booking confirmation email",
                    e
            );
        }
    }
}