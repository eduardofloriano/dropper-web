package br.com.dropper.web.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.dropper.web.dao.ImagemDAO;
import br.com.dropper.web.model.Imagem;
import br.com.dropper.web.util.JpaUtil;

@WebServlet("/images/*")
public class ImagemServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3454570844905974553L;
	
	
	private EntityManager em = new JpaUtil().getEntityManager();
	private ImagemDAO imagemDAO = new ImagemDAO(em);
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		
        String imageId = String.valueOf(request.getPathInfo().substring(1)); // Gets string that goes after "/images/".
        //UploadedImage image = cms.findImage(imageId); // Get Image from DB.
        Imagem imagem = imagemDAO.obterImagemPorId(Integer.parseInt(imageId));
        
        
        
        response.setHeader("Content-Type", getServletContext().getMimeType(imagem.getNome()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + imagem.getNome() + "\"");

        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            input = new BufferedInputStream(new ByteArrayInputStream(imagem.getData()) ); // Creates buffered input stream.
            output = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[8192];
            for (int length = 0; (length = input.read(buffer)) > 0;) {
                output.write(buffer, 0, length);
            }
        } finally {
            if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
            if (input != null) try { input.close(); } catch (IOException logOrIgnore) {}
        }
    }
	
	
	

}
