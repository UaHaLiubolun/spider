public class Verify {

    public static void main(String[] args) {

//        SessionDownload sessionDownload = SessionDownload.getInstance();
//        Request request = new Request("http://weixin.sogou.com/weixin?type=1&s_from=input&ie=utf8&_sug_=n&_sug_type_=&query=qdrsj12333 ");
//        request.putExtra("UnlockType", "sogou");
//        sessionDownload.download(request, Site.me().toTask());
//        int num = 100;
//        String url = "https://weixin.sogou.com/antispider/util/seccode.php?tc=";
//        for (int i = 0; i < num; i++) {
//            try {
//                byte[] imgs = sessionDownload.getImg(url + i, "sogou");
//                FileImageOutputStream imageOutput = new FileImageOutputStream(new File("C:\\Users\\Jhon\\Pictures\\imgs\\" + i + ".jpg"));//打开输入流
//                imageOutput.write(imgs, 0, imgs.length);//将byte写入硬盘
//                imageOutput.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

//        ITesseract instance = new Tesseract();
//        try {
//            instance.setDatapath("C:\\Users\\Jhon\\Downloads\\tessdata-master");
//            instance.setLanguage("eng");
//            for (int i = 0; i < num; i++) {
//                String code = instance.doOCR(new File("C:\\Users\\Jhon\\Pictures\\imgs\\" + i + ".jpg"));
//                System.out.println(i + ": " + code);
//            }
//        } catch (TesseractException e) {
//            e.printStackTrace();
//        }
    }
}
