package com.javaweb.web.rbac.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.javaweb.entity.rbac.Module;
import com.javaweb.entity.rbac.Role;
import com.javaweb.entity.rbac.User;
import com.javaweb.handler.FunctionAndOperationHandler;
import com.javaweb.util.common.StringUtil;
import com.javaweb.util.self.Constant;
import com.javaweb.util.self.ShiroUtil;
import com.javaweb.view.rbac.ModuleListVO;
import com.javaweb.web.rbac.service.UserService;
import com.javaweb.web.websocket.ChartController;

//该路径下的访问对所有人开放
@Controller
@RequestMapping(value="/")
/**@EnableScheduling//开启任务支持*/
/**
@Configuration//声明是一个配置类
@PropertySource("classpath:config/props/jdbc.properties")
@PropertySource(value = {"classpath:config/props/message.properties", "classpath:config/props/commonprops.properties"},ignoreResourceNotFound = true)
*/
/**tomcat的server.xml中为防止中文乱码,可以加上:URIEncoding="UTF-8"*/
public class AllOpenController {
	
	/**
	@Autowired
	private Environment env;
    //String environment = env.getProperty("jdbcDriverClassName");
	//Environment亦可用在service或dao层
	
	@Value("classpath:config/props/jdbc.properties")
	private org.springframework.core.io.Resource info;
    //info.getInputStream()
	
	@Value("#{ T(java.lang.Math).random() * 100.0 }")
    private double randomNumber;

	@Scheduled(cron = "0 22 11 ? * *"  )//每天上午11点22执行
	@Scheduled(fixedRate = 5000)//服务器加载controller后每5秒执行一次
	public void schedule(){
		System.out.println("定时任务被执行了");
		//导出
		String sql = "mysqldump -P 3308 -h 101.37.84.120 -u root -proot myDataBase --default-character-set=utf8 --lock-tables=false --result-file=D:\\test.sql";
		//String sql = "mysqldump -P 3306 -h localhost -u root -proot myDataBase --result-file=D:\\test.sql";
		//String sql = "mysqldump -P 3306 -h localhost -u root -proot myDataBase>D:\\test.sql";
		//String sql = Thread.currentThread().getContextClassLoader().getResource("mysql.sh").toString().replaceFirst("file:", "");  
		Runtime.getRuntime().exec(sql);//执行cmd命令,mysql数据库数据导出
		//导入
		String sql_1 = "mysql -P 3308 -h 101.37.84.120 -u root -proot test --default-character-set=utf8";
		String sql_2 = "use test";
		String sql_3 = "source D:\\test.sql";
		Process process = Runtime.getRuntime().exec(sql_1);
		OutputStream os = process.getOutputStream();  
	    OutputStreamWriter writer = new OutputStreamWriter(os);  
	    writer.write(sql_2 + "\r\n" + sql_3);  
	    writer.flush();  
	    writer.close();  
	    os.close(); 
	}
	
	@PostConstruct
	public void init(){
		System.out.println("我被执行了");
	}
	
	@PreDestroy
	public void destroy(){
		System.out.println("我被销毁了");
	}
	*/
	
	@Autowired//@Resource(name="userServiceImpl")
	private UserService userService;
	
	/**
	@RequestBody User user，比如user中有个Date日期，但是采用JSON的话，前端传来的日期就是字符串，那么可以采用以下形式转换：
	@InitBinder 
	public void initBinder(WebDataBinder binder) { 
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		//dateFormat.setLenient(false); 
		SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		datetimeFormat.setLenient(false); 
		//binder.registerCustomEditor(java.sql.Timestamp.class, new CustomDateEditor(dateFormat, true)); 
		binder.registerCustomEditor(java.util.Date.class,new CustomDateEditor(datetimeFormat, true)); 
	} 
	*/
	
	/**
	@RequestMapping(method=RequestMethod.GET,value="/get/{number}",produces={DataFormat.JSON})
	@ResponseBody
	public ResponseEntity<Resource<Object>> getTest(HttpServletRequest request, 
								                    HttpServletResponse response,
								                    @PathVariable int number){
		try{
			//System.out.println("token:"+request.getHeader("token"));
			ObjectMapper mapper = new ObjectMapper();
			String result = mapper.writeValueAsString(ts.getAll());
			Link link = linkTo(methodOn(DemoController.class).getTest(request, response, number)).withSelfRel();
			return HateoasUtil.getResponseEntity(result,link);
		}catch(Exception e){
			return null;
		}
	}
	*/
	
	/**
	httpServletRequest.getRequestURI()             /yuhong_server/app/html/home.html
	httpServletRequest.getRequestURL().toString()  http://localhost:8080/yuhong_server/app/html/home.html 
	httpServletRequest.getServletPath()            /app/html/home.html
	*/
	
	@PostMapping(value="/userWebLogin",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String login(HttpServletRequest request, 
			  			HttpServletResponse response,
			  			@RequestBody JsonNode jsonNode
			  			/** @RequestBody User user */
			  			/** @ModelAttribute User user */) {
		JSONObject jo = new JSONObject();
		try{
			//Xyz xyz = mapper.readValue(jsonNode.toString(), Xyz.class);
			/** JSONArray相关转化常用的方法如下：
			    List<ProjectMaterialsOut> list = (List<ProjectMaterialsOut>)JSONArray.toCollection(ja, ProjectMaterialsOut.class);
			    JSONArray ja = JSONArray.fromObject(JSONArray.toArray(ja,c));	
			 */
			String username = jsonNode.get("username").asText();//用户名
			String password = jsonNode.get("password").asText();//密码
			String code = jsonNode.get("code").asText();//这个是用户输入的验证码
			String sessionCode = getSessionCode();//这个是session中的验证码
			if(!code.equalsIgnoreCase(sessionCode)){//验证码校验忽略大小写
				jo.put(Constant.MESSAGE, "验证码错误");
				jo.put(Constant.STATUS, Constant.STATUS_FAIL);
			}else{
				Map<String,String> map = new HashMap<>();
				map.put("username", username);
				map.put("password", new SimpleHash("SHA-1", username, password).toString());
				User user = userService.getUserByUsernameAndPassword(map);
				if(user==null){
					jo.put(Constant.MESSAGE, "用户名或密码错误");
					jo.put(Constant.STATUS, Constant.STATUS_FAIL);
				}else{
					//同一账号登录后不能再登录的实现,未经大量验证,效果有待后续观察
					if(ChartController.user.get(username)!=null){
						jo.put(Constant.MESSAGE, "该账号已登录");
				    	jo.put(Constant.STATUS, Constant.STATUS_FAIL);
					}else{
						//shiro加入身份验证
					    UsernamePasswordToken token = new UsernamePasswordToken(map.get("username"), map.get("password")); 
				    	ShiroUtil.getSubject().login(token);
						//获得角色信息
						List<Role> roleList = userService.getUserRoles(user.getUserid());
						if(roleList.size()>0){
							int superAdminFlag = getSuperAdminFlag(roleList);
							//moduleList = userService.getUserMenu(roleList,superAdminFlag);
							//operationList = userService.getUserOperation(roleList,superAdminFlag);
							List<Module> allModuleList = userService.getUserAllModule(roleList, superAdminFlag);
							ModuleListVO moduleListVO = getNeedList(allModuleList);
							//获得当前角色左侧树形菜单列表
							List<Module> moduleList = moduleListVO.getModuleList();
							//获得当前角色右侧菜单对应的所有操作列表
							List<Module> operationList = moduleListVO.getOperationList();
							//获得顶层菜单列表,用于对关键操作进行路径检查
							List<Module> topModuleList = moduleListVO.getTopModuleList();
							moduleList = FunctionAndOperationHandler.setTreeList(moduleList, null);
							//获得权限路径检查的正则表达式
							String authorityChaekPath = getAuthorityChaekPath(topModuleList);
							//获得服务器IP地址及端口号
							String ipAddressAndPort = getIpAddressAndPort(request);
							ShiroUtil.setAttribute(Constant.SESSION_USER, user);
							ShiroUtil.setAttribute(Constant.SESSION_ROLE, roleList);
							ShiroUtil.setAttribute(Constant.SESSION_MODULE, moduleList);
							ShiroUtil.setAttribute(Constant.SESSION_OPERATION, operationList);
							ShiroUtil.setAttribute(Constant.AUTHORITY_CHAEK_PATH, authorityChaekPath);
							//ShiroUtil.setAttribute(Constant.IP_ADDRESS_PORT,ipAddressAndPort);
							jo.put(Constant.MESSAGE, "登录成功");
							jo.put("user", user);
							jo.put("role", roleList);
							jo.put("module", moduleList);
							jo.put("operation", operationList);
							jo.put("ipAddressPort", ipAddressAndPort);
							jo.put(Constant.STATUS, Constant.STATUS_SUCCESS);
						}else{
							jo.put(Constant.MESSAGE, "该用户未分配角色");
					    	jo.put(Constant.STATUS, Constant.STATUS_FAIL);
						}
					}
				}
			}
		}catch(Exception e){
			jo.put(Constant.MESSAGE, "服务器内部异常");
	    	jo.put(Constant.STATUS, Constant.STATUS_FAIL);
		}
		//从后台代码获取国际化信息
		//ResourceBundle resourceBundle = ResourceBundleHandler.getResourceBundle(request);
		//System.out.println(resourceBundle.getString("username"));
		return jo.toString();
	}
	
	//获得session中的验证码信息
	private String getSessionCode(){
		/**
		String result = "";
		Object sessionCode = ShiroUtil.getAttribute(Constant.SESSION_SECURITY_CODE);
		if(sessionCode!=null){
			result = sessionCode.toString();
		}
		*/
		String result = Optional.ofNullable(ShiroUtil.getAttribute(Constant.SESSION_SECURITY_CODE))
					.map(Object::toString).orElse("");
		return result;
	}
	
	//获得需要的数据
	private ModuleListVO getNeedList(List<Module> allModuleList){
		//Map<Integer,Map<Integer,List<Module>>> streamMap = allModuleList.stream().collect(Collectors.groupingBy(Module::getModuletype,Collectors.collectingAndThen(Collectors.groupingBy(Module::getLevels),null)));
		//获得当前角色左侧树形菜单列表
		List<Module> moduleList = new ArrayList<>();
		//获得当前角色右侧菜单对应的所有操作列表
		List<Module> operationList = new ArrayList<>();
		//获得顶层菜单列表,用于对关键操作进行路径检查
		List<Module> topModuleList = new ArrayList<>();
		allModuleList.stream().forEach(module->{
			int type = module.getModuletype();
			if(type==1){//type=1表示功能
				moduleList.add(module);
				if(module.getLevels()==1){//levels=1表示根节点
					topModuleList.add(module);
				}
			}else if(type==2){//type=2表示操作
				operationList.add(module);
			}
		});
		ModuleListVO moduleListVO = new ModuleListVO();
		moduleListVO.setModuleList(moduleList);
		moduleListVO.setOperationList(operationList);
		moduleListVO.setTopModuleList(topModuleList);
		return moduleListVO;
	}
	
	//根据用户角色判断是否是顶级管理员
	private int getSuperAdminFlag(List<Role> roleList){
		/**
		int superAdminFlag = 0;
		for (int i = 0; i < roleList.size(); i++) {
			if(roleList.get(i).getRoleid().equals(Constant.ROLE_ADMIN_ID)){
				superAdminFlag = 1;
				break;
			}
		}
		return superAdminFlag;
		*/
		boolean resultFlag = roleList.stream().anyMatch(AllOpenController::isRoleAdminId);
		return resultFlag==true?1:0;
	}
	
	//判断是否是顶级管理员
	private static boolean isRoleAdminId(Role role){
		return Constant.ROLE_ADMIN_ID.equals(role.getRoleid());
	}
	
	//获得IP地址和端口
	private String getIpAddressAndPort(HttpServletRequest request){
		String ipAddress = request.getHeader("x-forwarded-for");  
        if(StringUtil.isEmptyOrNull(ipAddress)||"unknown".equalsIgnoreCase(ipAddress)){  
        	ipAddress=request.getHeader("Proxy-Client-IP");  
        }  
        if(StringUtil.isEmptyOrNull(ipAddress)||"unknown".equalsIgnoreCase(ipAddress)){  
        	ipAddress=request.getHeader("WL-Proxy-Client-IP");  
        }  
        if(StringUtil.isEmptyOrNull(ipAddress)||"unknown".equalsIgnoreCase(ipAddress)){  
        	ipAddress=request.getRemoteAddr();  
        }  
        //多个路由时,取第一个非unknown的ip  
        final String[] strArry = ipAddress.split(",");  
        for(String str:strArry) {  
            if (!"unknown".equalsIgnoreCase(str)) {  
            	ipAddress = str;  
                break;  
            }  
        }  
        String ipAddressAndPort = ipAddress+":"+request.getServerPort();
        /**		
		String ipAddressAndPort = "";
		try {
			InetAddress addr = InetAddress.getLocalHost();
			ipAddressAndPort = addr.getHostAddress()+":"+request.getServerPort();
		} catch (UnknownHostException e) {
			ipAddressAndPort = request.getServerName()+":"+request.getServerPort();
		}
		*/
		return ipAddressAndPort;
	}
	
	//自定义权限路径检查规则
	private String getAuthorityChaekPath(List<Module> topModuleList){
		//"/web/sys.*|/web/wei.*|/web/tools.*"
		/**
		String authorityChaekPath = "";
		for(int i=0;i<topModuleList.size();i++){
			authorityChaekPath+=topModuleList.get(i).getModuleurl()+".*|";
		}
		if(!"".equals(authorityChaekPath)){
			authorityChaekPath = authorityChaekPath.substring(0, authorityChaekPath.length()-1);
		}
		return authorityChaekPath;
		*/
		return topModuleList.stream().map(module->module.getModuleurl())
		                    .collect(Collectors.joining(".*|","",topModuleList.size()==0?"":".*"));
	}
	
	//获取验证码
	@GetMapping(value="/userWebGetCode")
	@ResponseBody
	public void getCode(HttpServletRequest request, 
  			            HttpServletResponse response){
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String code = drawImg(output);
		ShiroUtil.setAttribute(Constant.SESSION_SECURITY_CODE, code);
		try {
			ServletOutputStream out = response.getOutputStream();
			output.writeTo(out);
		} catch (IOException e) {
			//do nothing
		}
	}
	
	//登出
	@GetMapping(value="/loginOut")
	public void loginOut(HttpServletRequest request, 
  			             HttpServletResponse response){
		Session session = ShiroUtil.getSession();
		try{
			//专为websocket做的处理
			ChartController.user.remove(((User)session.getAttribute(Constant.SESSION_USER)).getUsername());
		}catch(Exception e){
			//出现异常暂时不管
		}
		Collection<Object> collections = session.getAttributeKeys();
		for(Object key:collections){
			session.removeAttribute(key);
		}
	}
	
	//检查session是否在
	@GetMapping(value="/checkSessionExist")
	@ResponseBody
	public String checkSessionExist(HttpServletRequest request, 
  			             	        HttpServletResponse response){
		Session session = ShiroUtil.getSession();
		Object object = session.getAttribute(Constant.SESSION_USER);
		JSONObject jsonObject = new JSONObject();
		if(object==null){
			jsonObject.put(Constant.STATUS, Constant.STATUS_FAIL);
		}else{
			jsonObject.put(Constant.STATUS, Constant.STATUS_SUCCESS);
		}
		return jsonObject.toString();
	}
	
	//生成图片
	private String drawImg(ByteArrayOutputStream output){
		//final int verifyCodeLength = 10;
		final int verifyCodeLength = 4;
		String code = getVerifyCode(verifyCodeLength);
		//int width = 125;
		int width = 70;
		int height = 25;
		BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
		Font font = new Font("Times New Roman",Font.PLAIN,20);
		Graphics2D g = bi.createGraphics();
		g.setFont(font);
		g.setColor(new Color(66,2,82));
		g.setBackground(new Color(226,226,240));
		g.clearRect(0, 0, width, height);
		FontRenderContext context = g.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(code, context);
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = bounds.getY();
		double baseY = y - ascent;
		g.drawString(code, (int)x, (int)baseY);
		g.dispose();
		try {
			ImageIO.write(bi, "jpg", output);
		} catch (IOException e) {
			//do nothing
		}
		return code;
	}
	
	//获得验证码字符串
	private String getVerifyCode(int verifyCodeLength){
		//final String s = "Aa-Bb0=Cc+Dd1~Ee;Ff2@GgHh3#Jj:Kk4$Ll[Mm5%Nn]Pp6^Rr{Ss7&Tt}Uu8*Vv<Ww9(Xx>Yy?Zz)";
		final String s = "ab1cd2ef3gh4jk5mn6op7qr8st9uv0wxyz";
		final int seedLength = s.length();
		StringBuffer stringBuffer = new StringBuffer();
		for(int i=0;i<verifyCodeLength;i++){
			stringBuffer.append(s.charAt((int)(Math.random()*seedLength)));
		}
		return stringBuffer.toString();
	}
	
	/**
	//参考
	//前端页面的路径写为:<img src="/identifyCode" />
	@RequestMapping("/identifyCode")
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int width = 50;
		int height = 50;
		BufferedImage buffImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = buffImage.createGraphics();
		//设定图像背景色(因为是做背景，所以偏淡)
		g2d.setColor(getRandColor(200, 250));
		g2d.fillRect(0, 0, width, height);
		//创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Times New Roman", Font.HANGING_BASELINE, 28);
		//设置字体。
		g2d.setFont(font);
		//画边框。
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, 0, width - 1, height - 1);
		//随机产生155条干扰线，使图象中的认证码不易被其它程序探测到。
		//g.setColor(Color.GRAY);
		//创建一个随机数生成器类。
		Random random = new Random();
		g2d.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g2d.drawLine(x, y, x + xl, y + yl);
		}
		//randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		StringBuffer randomCode = new StringBuffer();
		//设置默认生成4个验证码
		int length = 4;
		//设置备选验证码:包括"a-z"和数字"0-9"
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		int size = base.length();
		//随机产生4位数字的验证码。
		for (int i = 0; i < length; i++) {
			//得到随机产生的验证码数字。
			int start = random.nextInt(size);
			String strRand = base.substring(start, start + 1);
			//用随机产生的颜色将验证码绘制到图像中。
			//生成随机颜色(因为是做前景，所以偏深)
			//g.setColor(getRandColor(1, 100));
			//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g2d.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g2d.drawString(strRand, 15 * i + 6, 24);
			//将产生的四个随机数组合在一起。
			randomCode.append(strRand);
		}
		//System.out.println(randomCode.toString());//aE39
		//将四位数字的验证码保存到Session中。
		//request.getSession().setAttribute("rand", randomCode.toString());
		//图象生效
		g2d.dispose();
		//禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		//将图像输出到Servlet输出流中。
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(buffImage, "jpeg", sos);
		sos.flush();
		sos.close();
	}
	
	public Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	*/
	
}
