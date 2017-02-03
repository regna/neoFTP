package org.apache.poi.wp.usermodel;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



public class mail {

	private static void addAttachment(Multipart multipart, String filename) throws MessagingException
	{
	    DataSource source = new FileDataSource(filename);
	    BodyPart messageBodyPart = new MimeBodyPart();        
	    messageBodyPart.setDataHandler(new DataHandler(source));
	    messageBodyPart.setFileName(filename);
	    multipart.addBodyPart(messageBodyPart);
	}
	
public static void test(String cliente, String items){
    Properties props = System.getProperties();
    props.put("mail.smtp.starttls.enable", true); 
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.user", "username");
    props.put("mail.smtp.password", "password");
    props.put("mail.smtp.port", "25");
    props.put("mail.smtp.auth", true);



    Session session = Session.getInstance(props,null);
    MimeMessage message = new MimeMessage(session);

    System.out.println("Port: "+session.getProperty("mail.smtp.port"));

    
    try {
        InternetAddress from = new InternetAddress("lauvizzari@neotel.com.ar");
        message.setSubject("FTP "+cliente);
        message.setFrom(from);
        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse("nlopez@neotel.com.ar"));

        
        Multipart multipart = new MimeMultipart("alternative");

      
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("some text to send");

        
        multipart.addBodyPart(messageBodyPart);

        
        messageBodyPart = new MimeBodyPart();
        String htmlMessage = "Estimados de "+cliente+ "\n\n Nos llego la alerta del sistema de que los items:" +items+ " no están teniendo un backup via FTP. \nPara esto sugerimos a nuestros clientes que generen una tarea diaria de extracción vía FTP de los archivos claves del sistemas. De esta forma tenemos un respaldo ante cualquier contingencia que ocurra.\n\nAdjunto el manual de un soft que se utilizan en el grueso de nuestros clientes para realizar este respaldo diariamente.No es excluyente que sea este el soft a utilizar pueden utilizar el que mas les convenga. \n Al ser un tema tan critico tengo que adjuntarles el comunicado sobre el tratamiento de las alertas de backup y el manual de backup.\n\n\n	Saludos att Neotel";
        messageBodyPart.setContent(htmlMessage, "text/plain");
        

        addAttachment(multipart, "instructivo.docx");
        addAttachment(multipart, "notificacion.docx");
        
        multipart.addBodyPart(messageBodyPart);

        
        message.setContent(multipart);

       
        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com", "lvizzari@neotel.com.ar", "magate101458");
        System.out.println("Transport: "+transport.toString());
        transport.sendMessage(message, message.getAllRecipients());


    } catch (AddressException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (MessagingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}


}
