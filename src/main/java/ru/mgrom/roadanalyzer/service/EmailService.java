package ru.mgrom.roadanalyzer.service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail, String tittle, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(tittle);
        message.setText(text);
        mailSender.send(message);
    }
}
