package org.web;  
  
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.entity.Users;
import org.json.JSONObject;
import org.service.IUserService;
import org.service.impl.UserServiceImpl;

import com.baidu.aip.face.AipFace;

import sun.misc.BASE64Decoder;  
  
public class FaceServlet extends HttpServlet {  
  
      
    /** 
     * (非 Javadoc) 
    * <p>Description(描述):get方法，主要调用post </p> 
    * <p>Title: doGet</p> 
    * @param request 
    * @param response 
    * @throws ServletException 
    * @throws IOException 
    * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse) 
     */  
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
            doPost(request, response);  
    }  
  
    /** 
     * (非 Javadoc) 
    * <p>Description(描述):post方法，主要进行业务操作 </p> 
    * <p>Title: doPost</p> 
    * @param request 
    * @param response 
    * @throws ServletException 
    * @throws IOException 
    * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse) 
     */  
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
  
        //防止乱码  
        response.setContentType("text/html;charset=utf-8");  
        request.setCharacterEncoding("utf-8");  
        response.setCharacterEncoding("utf-8");  
          
          
        String      img         =       request.getParameter("img");            //图像数据  
        String      username    =       request.getParameter("username");       //用户名
        String      password    =       request.getParameter("password");       //密码
        String      tag         =       request.getParameter("tag");  
//        String APP_ID = "9802974";  
//        String API_KEY = "OVYw5Ok0y9U8n6CfVPYt0wfZ";  
//        String SECRET_KEY = "aCN3lupCarq3rC9G8Rylqz1d36Towp8G";
        String APP_ID = "11088804";  
        String API_KEY = "e1I2nVP79uZrsd1byLazESk9";  
        String SECRET_KEY = "Av8WewxfyIMqAfXhRzxlgBAlbzFEqmL8";
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);  
        if(tag.equals("reg")){  
            //注册  
            face(username,password, img, response,request,client);  
        }else if(tag.endsWith("login")){  
            //登陆  
            login(img, response, username,request,client);  
        }  
          
    }  
      
    /** 
     *  
    * @Title: face 
    * @Description: 该方法的主要作用：注册 
    * 1.将base64编码的图片保存 
    * 2.将图片路径保存在数据库里面 
    * 3.将人脸图片识别在人脸库中注册 
    * @param   设定文件   
    * @return  返回类型：void    
    * @throws 
     */  
    public void face(String username,String password,String img,HttpServletResponse response,HttpServletRequest request,AipFace client) {  
        Users user = new Users();  
        IUserService userService = new UserServiceImpl();  
        // 图片名称  
        String fileName = System.currentTimeMillis() + ".png";  
        String basePath = request.getSession().getServletContext()  
                .getRealPath("picture/");  
  
        // 往数据库里面插入注册信息  
        user.setId(((Long)System.currentTimeMillis()).intValue());  
        user.setUsername(username); 
        user.setPassword(password);
        user.setHeadphoto("/picture/" + fileName);  
        userService.save(user); 
        
        File dir = new File(basePath);
        this.judeDirExists(dir);
        
        // 往服务器里面上传图片  
        GenerateImage(img, basePath + "/" + fileName);  
        // 给人脸库中加入一个脸  
        boolean flag = facesetAddUser(client, basePath + "\\" + fileName,  
                username);  
        try {  
            PrintWriter out = response.getWriter();  
            // 中文乱码，写个英文比较专业 哈哈  
  
            if (flag == false) {  
                out.print("请把脸放上!!");//请把两脸放上  
            } else {  
                out.print("注册成功!!");  //注册成功  
            }  
        } catch (IOException e) {  
            // TODO 异常执行块！  
            e.printStackTrace();  
        }  
    }  
  
  
    /** 
     *  
    * @Title: login 
    * @Description: 该方法的主要作用：登陆 
    * 实现原理： 
    * 1.从前台接收用户名 
    * 2.然后把从前台传过来的图片先上传到服务器 
    * 3.把上传的这张人脸与人脸库中的人脸对应 
    * @param  设定文件   
    * @return  返回类型：String    
    * @throws 
     */  
    public void login(String img,HttpServletResponse response,String username,HttpServletRequest request,AipFace client) {  
        // 图片名称  
        String fileName = System.currentTimeMillis() + ".png";  
        String basePath = request.getSession().getServletContext()  
                .getRealPath("picture/");  
        // 往服务器里面上传图片  
        GenerateImage(img, basePath + "/" + fileName);  
        response.setContentType("text/html,charset=utf-8;");  
        try {  
            PrintWriter out = response.getWriter();  
            Double result = verifyUser(client, basePath + "/" + fileName,username);  
            if (result > 90) {  
                // 匹配成功  
                out.print("登陆成功");    
            } else {  
                //匹配失败  
                out.print("登陆失败");  
  
            }  
        } catch (IOException e) {  
            // TODO 异常执行块！  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     *  
        * @Title: GenerateImage 
        * @Description: 该方法的主要作用：// 对字节数组字符串进行Base64解码并生成图片 
        * @param  @param imgStr 
        * @param  @param imgFilePath 
        * @param  @return 设定文件   
        * @return  返回类型：boolean    
        * @throws 
         */  
    public boolean GenerateImage(String imgStr, String imgFilePath) {  
        if (imgStr == null) // 图像数据为空  
            return false;  
        BASE64Decoder decoder = new BASE64Decoder();  
        try {  
            // Base64解码  
            byte[] bytes = decoder.decodeBuffer(imgStr);  
            for (int i = 0; i < bytes.length; ++i) {  
                if (bytes[i] < 0) {// 调整异常数据  
                    bytes[i] += 256;  
                }  
            }  
            // 生成jpeg图片  
            OutputStream out = new FileOutputStream(imgFilePath);  
            out.write(bytes);  
            out.flush();  
            out.close();  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
      
        /**  
         * @Title: facesetAddUser 
        * @Description: 该方法的主要作用：人脸注册,给人脸库中注册一个人脸 
        * @param  @param client 设定文件   
        * @return  返回类型：void    
        * @throws 
         */  
    public boolean facesetAddUser(AipFace client, String path, String username) {  
        // 参数为数据库中注册的人脸  
        HashMap<String, String> options = new HashMap<String, String>();  
        JSONObject res = client.addUser(username, "test_users_info",  
                Arrays.asList("group1", "group2"), path, options); 
        
//        JSONObject res = client.detect(path, options);
        System.out.println(res);
         System.out.println(res.toString(2));  
        // {"log_id": 3621903690062422}  
        if (res.keySet().contains("error_code")) {  
            return false;  
        }  
        return true;  
  
    }  
    /** 
     *  
    * @Title: verifyUser 
    * @Description: 该方法的主要作用：人脸认证 
    * @param  @param client 
    * @param  @param path 
    * @param  @param username 
    * @param  @return 设定文件   
    * @return  返回类型：Double    
    * @throws 
     */  
          
    public Double verifyUser(AipFace client, String path, String username) {  
//        HashMap<String, Object> options = new HashMap<String, Object>(1);  
//        options.put("top_num", 5);  
    	HashMap<String, String> options = new HashMap<String, String>();  
        /*try { 
            Thread.sleep(2000); 
        } catch (InterruptedException e) { 
            // TODO 异常执行块！ 
            e.printStackTrace(); 
        }*/  
//        JSONObject res = client.verifyUser(username,  
//                Arrays.asList("group1", "group2"), path, options);  
//        String photoPath01 = "E/:Devoloper_Develop_Spase/eclipse_data/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/Face_Recognition_Demo02/picture/1523525107633.png";
//        String photoPath02 = "E/:Devoloper_Develop_Spase/eclipse_data/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/Face_Recognition_Demo02/picture/1523525364707.png";
    	String photoPath01 = "E:/photo/1523525107633.png";
    	String photoPath02 = "E:/photo/1523525364707.png";
        ArrayList<String> list = new ArrayList<String>();
        list.add(photoPath01);
        list.add(photoPath02);
        JSONObject res = client.match(list, options);
        System.out.println(res);
        JSONObject json = (JSONObject) res.getJSONArray("result").get(0);
        Iterator it = json.keys();
        String value = "";
        while(it.hasNext()){
            String key = (String) it.next();
            if ("score".equals(key)) {
            	//得到value的值
                value = json.get(key).toString();
			}
        }
        Double result = Double.parseDouble(value); 
        System.out.println(result);
        return result;  
    }  
  
    // 判断文件夹是否存在
    public static void judeDirExists(File file) {

        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("dir exists");
            } else {
                System.out.println("the same name file exists, can not create dir");
            }
        } else {
            System.out.println("dir not exists, create it ...");
            file.mkdir();
        }

    }
  
}  