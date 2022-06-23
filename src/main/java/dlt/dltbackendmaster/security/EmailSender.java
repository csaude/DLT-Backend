package dlt.dltbackendmaster.security;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSender
{
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String username, String password, String email, String link, boolean isNewUser)
                    throws UnsupportedEncodingException, MessagingException {
        String subject = "";
        String content = "";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("histech@fgh.org.mz", "Helpdesk do SIS");
        helper.setTo(email);

        if (isNewUser) {
            subject = "DLT: Confirmação de Cadastro";
            content = "<p>Olá,</p>" 
                      + "<p>Foi cadastrado na plataforma do Dreams com os seguintes detalhes:</p>"
                      + "<p>Username: <b>" + username + "</b></p>"
                      + "<p>Password: <b>" + password + "</b></p>"
                      + "<p>Será solicitado a alterar a senha no primeiro login.</p>";
        } else {
            subject = "DLT: Confirmar Alteração de Password";
            content = "<p>Olá,</p>"
                      + "<p>Recebeu solicitação para confirmar a alteração de password do utilizador <b>" + username + "</b></p>"
                      + "<p>Faça clique no link abaixo para confirmar a alteração da password</p>"
                      + "<p><b><a href=\"" + link + "\">Confirmar Alteração de Password</a></b></p>"
                      + "<p>Ignore este e-mail se não reconhece o utilizador</p>";
        }
        helper.setSubject(subject);
        helper.setText(content, true);
        javaMailSender.send(message);
    }
}