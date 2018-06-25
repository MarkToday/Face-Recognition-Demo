<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>登陆</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!-- 
    <link rel="stylesheet" type="text/css" href="styles.css"> 
    -->
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
			context.drawImage(video, 0, 0);
	
			var imgData = canvans.toDataURL();
			//获取图像在前端截取22位以后的字符串作为图像数据  
			var imgData1 = imgData.substring(22);
	
			var username = $("#username").val();
			$.ajax({
				type : "post",
				url : "FaceServlet?tag=login",
				data : {
					"img" : imgData1,
					"username" : username
				},
				success : function(data) {
					alert(data);
				},
				error : function(msg) {
					alert("检测到不是你的脸");
				}
			});
	
		}
	</script>
	<style type="text/css">
	     	body {
				/*设置body背景颜色*/
				background: #cddc39;
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
	<div class="box">
		<h2>登陆</h2>
		输入用户名:
		<input type="text" name="username" id="username" />
		<br /> 
		献上你的脸：
		<div id="support"></div>
		<video id="video" width="120" height="90"
			style="border:1px solid red;border-radius: 800px;" autoplay></video>
	
		<canvas
			style="border:1px solid blue;border-radius: 800px;width:120px;height:80px;"
			id="canvas">
		</canvas>
		<br />
		<input type="button" value="准备好了就确认" id="snap" />
		<a href="index.jsp">注册</a>
	</div>
</body>
</html>
