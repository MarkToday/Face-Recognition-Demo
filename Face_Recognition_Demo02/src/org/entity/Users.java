/**   
* @Title: Users.java 
* @Package org.entity 
* @Description: TODO该方法的主要作用： 
* @author A18ccms A18ccms_gmail_com   
* @date 2017-9-22 下午7:01:09 
* @version V1.0   
*/  
package org.entity;  
  
 /**    
 *     
 * 项目名称：test_face_photo    
 * 类名称：Users    
 * 类描述：   实体类 
 * 创建人：Mu Xiongxiong   
 * 创建时间：2017-9-22 下午7:01:09    
 * 修改人：Mu Xiongxiong    
 * 修改时间：2017-9-22 下午7:01:09    
 * 修改备注：    
 * @version     
 *     
 */  
public class Users {  
      
    private Integer id ;            //编号  
    private String username;        //用户名  
    private String password;        //密码  
    private String headphoto;       //头像  
    public Integer getId() {  
        return id;  
    }  
    public void setId(Integer id) {  
        this.id = id;  
    }  
    public String getUsername() {  
        return username;  
    }  
    public void setUsername(String username) {  
        this.username = username;  
    }  
    public String getPassword() {  
        return password;  
    }  
    public void setPassword(String password) {  
        this.password = password;  
    }  
    public String getHeadphoto() {  
        return headphoto;  
    }  
    public void setHeadphoto(String headphoto) {  
        this.headphoto = headphoto;  
    }  
    public Users(Integer id, String username, String password, String headphoto) {  
        super();  
        this.id = id;  
        this.username = username;  
        this.password = password;  
        this.headphoto = headphoto;  
    }  
    public Users() {  
        super();  
    }  
      
      
  
}