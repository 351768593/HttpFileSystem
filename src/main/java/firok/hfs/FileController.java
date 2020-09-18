package firok.hfs;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <a>https://www.cnblogs.com/zhaoyan001/p/10953711.html</a>
 * @author Firok
 */
@RestController
@CrossOrigin
@RequestMapping("/fs")
public class FileController
{

	@Value("${upload.path}")
	private String pathUpload;//上传文件保存的本地目录，使用@Value获取全局配置文件中配置的属性值

	static boolean checked=false;
	static int sizeCache=10240;

	/**
	 * 文件上传，因为只是演示，所以使用 @ResponseBody 将结果直接返回给页面
	 */
	@PostMapping("/upload/{filename}")
	public Ret<?> uploadFile (
			@PathVariable(value = "filename") String filename,
//			@RequestParam("file") MultipartFile multipartFile,
			HttpServletRequest request) throws Exception
	{
//		if (multipartFile == null || multipartFile.isEmpty()) {
//			return Ret.fail("上传文件为空");
//		}
		if(!checked)
		{
			File parentDir=new File(pathUpload);
			if(!parentDir.exists()) checked=parentDir.mkdirs();
		}

		final int sizeCache= FileController.sizeCache;

		Collection<Part> parts=request.getParts();
//		System.out.println("size : "+parts.size());
		if(parts.size()!=1) throw new RuntimeException("请求报文有误");
		List<Part> list=new ArrayList<>(parts);
		Part part=list.get(0);

		long size=part.getSize();

		//		System.out.println("filename: "+name2save);

		InputStream is=part.getInputStream();
		File saveFile = new File(pathUpload, filename);
		try(FileOutputStream ofs=new FileOutputStream(saveFile))
		{
			while(size>0)
			{
				int len=size>=sizeCache?sizeCache:(int)(size%sizeCache);
				byte[] cache=new byte[len];
				is.read(cache);
				ofs.write(cache);
				size-=sizeCache;
			}

		}

//		System.out.printf("文件名[%s] 文件大小[%s]\n",filename,multipartFile);

//		System.out.println("文件保存路径：" + saveFile.getPath());
//		multipartFile.transferTo(saveFile);
//		multipartFile.transferTo(saveFile.getAbsoluteFile());//文件保存

		return Ret.success(saveFile.getPath());
	}


	@GetMapping(value="/download/{filename}")
	public void download(
			@PathVariable("filename") String filename,
			HttpServletResponse response) throws IOException
	{

		final int sizeCache= FileController.sizeCache;
		File file = new File(pathUpload,filename);

		try(FileInputStream ifs = new FileInputStream(file))
		{
			long size=file.length();
			String realname=file.getName();
			realname=realname.substring(realname.indexOf("_")+1);

			System.out.println(realname);

			OutputStream os = response.getOutputStream();
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-Disposition",String.format("attachment; filename=\"%s\"",realname));
			response.addHeader("Content-Length", String.valueOf(size));

			while(size>0)
			{
				int len=size>=sizeCache?sizeCache:(int)(size%sizeCache);
				byte[] cache=new byte[len];
				ifs.read(cache);
				os.write(cache);
				size-=sizeCache;
			}
			os.flush();
			os.close();
		}
	}
}
