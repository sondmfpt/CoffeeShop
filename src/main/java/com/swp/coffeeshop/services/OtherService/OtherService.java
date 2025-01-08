package com.swp.coffeeshop.services.OtherService;

import java.io.*;
import java.util.Base64;

public class OtherService implements IOtherService {
    @Override
    public void saveImage(String base64Image, String path) {
        if (base64Image != null && base64Image.startsWith("data:image/png;base64,")) {
            // Xóa phần tiền tố "data:image/png;base64,"
            base64Image = base64Image.replace("data:image/png;base64,", "");

            // Giải mã dữ liệu base64 thành mảng byte
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            // Ghi dữ liệu byte vào tệp ảnh
            try (OutputStream outputStream = new FileOutputStream(new File(path))) {
                outputStream.write(imageBytes);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
