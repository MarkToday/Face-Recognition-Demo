<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<!DOCTYPE html>  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">  
<title>人脸注册</title>  
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>  
<script type="text/javascript" src="js/index.js"></script>  
<script type="text/javascript">  
            function CatchCode() {  
          //实际运用可不写，测试代 ， 为单击拍照按钮就获取了当前图像，有其他用途  
          var canvans = document.getElementById("canvas");  
          var video = document.getElementById("video");  
          var context = canvas.getContext("2d");  
   
          canvas.width = video.videoWidth;  
          canvas.height = video.videoHeight;  
          context.drawImage(video,0,0);  
            
          
          //把canvas图像转为img图片
          //canvas.toDataURL("image/png");
          //console.log("img.src:",canvas.toDataURL("image/png"));
          
          var imgData = canvans.toDataURL();  
            //获取图像在前端截取22位以后的字符串作为图像数据  
            var imgData1 = imgData.substring(22);  
              
            var username = $("#username").val(); 
            var password = $("#password").val(); 
            
            console.log("username:",username);
            console.log("password:",password);
            $.ajax({  
                    type: "post",  
                    url: "FaceServlet?tag=reg",  
                    data: {"img":imgData1,"username":username,"password":password},  
                    success: function(data){  
                        alert(data);  
                    },error:function(msg){  
                        alert("错误");  
                    }  
                });  
                  
                  
      }  
              
     </script>  
     <style type="text/css">
     	body {
			/*设置body背景颜色*/
			background: #8bc34a61;
		}
		h1 {
			/*设置标题字体颜色，字体阴影*/
			color:#ffffff;
			text-shadow: 2px 2px 5px #000;
			/*设置文本宽度*/
			width:385px;
			/*设置文本居中*/
			margin:0 auto;
			/*内容居中*/
			text-align: center;
		}
		
		.box{
			width: 450px;
			height: 350px;
			border: 1px solid #000;
			background: #fff;
			margin: 200px auto;
			text-align: center;
			box-shadow: 2px 2px 5px #000;
		}
     </style>
</head>  
<body>  
		<!-- 表单提交区域start -->
		<div class="box">
			<h2>注册</h2>
			<div>
				用户名：
				<input type="text" name="username" id="username" />
			</div>  
			<div>
				密码：
				<input type="text" name="password" id="password" />
			</div>
		    <br /> 
		    <div>
		    	录入头像：  
			    <div id="support">
			    </div> 
		    </div>
		           
		    <div id="contentHolder">  
		        <video id="video" width="120" height="90"  
		            style="border:1px solid red;border-radius: 800px;" autoplay></video>  
		  
		        <canvas style="border:1px solid blue;border-radius: 800px;width:120px;height:80px;"  
		            id="canvas"></canvas>  
		    </div>  
		    <br />  
		    <input type="button" value="确认" id="snap" />  
		    <br />  
		    <a href="login.jsp">点击登陆</a> 
		</div>		
		<!-- 表单提交区域end -->
</body>  
</html>