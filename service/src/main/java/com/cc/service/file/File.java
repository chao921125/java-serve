import java.io.File; import java.io.FileOutputStream; import java.io.IOException; import java.io.InputStream; import java.io.OutputStream; import java.util.List;

import javax.servlet.ServletException; import javax.servlet.annotation.MultipartConfig; import javax.servlet.annotation.WebServlet; import javax.servlet.http.HttpServlet; import javax.servlet.http.HttpServletRequest; import javax.servlet.http.HttpServletResponse; import javax.servlet.http.Part;

@WebServlet("/fileUpload") @MultipartConfig public class FileUploadServlet extends HttpServlet { private static final long serialVersionUID = 1L;

protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");

    final String saveDir = "/uploads";
    String appPath = request.getServletContext().getRealPath("");
    String savePath = appPath + File.separator + saveDir;

    File fileSaveDir = new File(savePath);
    if (!fileSaveDir.exists()) {
        fileSaveDir.mkdir();
    }

    String fileName = "";
    List<Part> fileParts = (List<Part>) request.getParts();
    for (Part part : fileParts) {
        fileName = extractFileName(part);
        part.write(savePath + File.separator + fileName);
    }
    response.getWriter().write("File " + fileName + " has been uploaded successfully!");
}

private String extractFileName(Part part) {
    String contentDisp = part.getHeader("content-disposition");
    String[] items = contentDisp.split(";");
    for (String s : items) {
        if (s.trim().startsWith("filename")) {
            return s.substring(s.indexOf("=") + 2, s.length() - 1);
        }
    }
    return "";
}
}