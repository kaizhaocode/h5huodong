package com.jeecg.p3.shaketicket.web.back;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.util.SystemTools;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeecg.p3.shaketicket.entity.WxActShaketicketRecord;
import com.jeecg.p3.shaketicket.service.WxActShaketicketRecordService;
import com.jeecg.p3.shaketicket.util.ContextHolderUtils;

 /**
 * 描述：</b>WxActShaketicketRecordController<br>抽奖记录表
 * @author pituo
 * @since：2015年12月22日 19时03分50秒 星期二 
 * @version:1.0
 */
@Controller
@RequestMapping("/shaketicket/back/wxActShaketicketRecord")
public class WxActShaketicketRecordController extends BaseController{
  @Autowired
  private WxActShaketicketRecordService wxActShaketicketRecordService;
  
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute WxActShaketicketRecord query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "shaketicket/back/wxActShaketicketRecord-list.vm";
		try {
			PageQuery<WxActShaketicketRecord> pageQuery = new PageQuery<WxActShaketicketRecord>();
		 	pageQuery.setPageNo(pageNo);
		 	pageQuery.setPageSize(pageSize);
			pageQuery.setQuery(query);
			velocityContext.put("wxActShaketicketRecord",query);
			String backurl =  ContextHolderUtils.getRequest().getParameter("backurl");//返回时的url
			velocityContext.put("backurl",backurl);
			velocityContext.put("pageInfos",SystemTools.convertPaginatedList(wxActShaketicketRecordService.queryPageList(pageQuery)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		ViewVelocity.view(request,response,viewName,velocityContext);
}

 /**
  * 详情
  * @return
  */
@RequestMapping(value="toDetail",method = RequestMethod.GET)
public void wxActShaketicketRecordDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "shaketicket/back/wxActShaketicketRecord-detail.vm";
		WxActShaketicketRecord wxActShaketicketRecord = wxActShaketicketRecordService.queryById(id);
		velocityContext.put("wxActShaketicketRecord",wxActShaketicketRecord);
		String backurl =  ContextHolderUtils.getRequest().getParameter("backurl");//返回时的url
		velocityContext.put("backurl",backurl);
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAdd",method ={RequestMethod.GET, RequestMethod.POST})
public void toAddDialog(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 VelocityContext velocityContext = new VelocityContext();
	 String viewName = "shaketicket/back/wxActShaketicketRecord-add.vm";
	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/doAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doAdd(@ModelAttribute WxActShaketicketRecord wxActShaketicketRecord){
	AjaxJson j = new AjaxJson();
	try {
		wxActShaketicketRecordService.doAdd(wxActShaketicketRecord);
		j.setMsg("保存成功");
	} catch (Exception e) {
		e.printStackTrace();
		j.setSuccess(false);
		j.setMsg("保存失败");
	}
	return j;
}

/**
 * 跳转到编辑页面
 * @return
 */
@RequestMapping(value="toEdit",method = RequestMethod.GET)
public void toEdit(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request) throws Exception{
		 VelocityContext velocityContext = new VelocityContext();
		 WxActShaketicketRecord wxActShaketicketRecord = wxActShaketicketRecordService.queryById(id);
		 velocityContext.put("wxActShaketicketRecord",wxActShaketicketRecord);
		 String viewName = "shaketicket/back/wxActShaketicketRecord-edit.vm";
		 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute WxActShaketicketRecord wxActShaketicketRecord){
	AjaxJson j = new AjaxJson();
	try {
		wxActShaketicketRecordService.doEdit(wxActShaketicketRecord);
		j.setMsg("编辑成功");
	} catch (Exception e) {
		e.printStackTrace();
		j.setSuccess(false);
		j.setMsg("编辑失败");
	}
	return j;
}


/**
 * 删除
 * @return
 */
@RequestMapping(value="doDelete",method = RequestMethod.GET)
@ResponseBody
public AjaxJson doDelete(@RequestParam(required = true, value = "id" ) String id){
		AjaxJson j = new AjaxJson();
		try {
			wxActShaketicketRecordService.doDelete(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}

/** 
 * 导出Excel 
 * @return 
 *  
 */ 
@RequestMapping(value = "/exportExcel")
public AjaxJson exportExcel(HttpServletRequest request,HttpServletResponse response){
	AjaxJson j = new AjaxJson();
	response.setCharacterEncoding("utf-8");
    response.setContentType("multipart/form-data");
    String fileName = "导出信息.xls";  
    try {  
    	response.setHeader("Content-disposition", "attachment; filename="  
				   + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
    	String actId =  ContextHolderUtils.getRequest().getParameter("actId");//返回时的url
        InputStream inputStream = wxActShaketicketRecordService.exportExcel(actId); 
        OutputStream os = response.getOutputStream();
        byte[] b = new byte[2048];
        int length;
        while ((length = inputStream.read(b)) > 0) {
            os.write(b, 0, length);
        }
         // 这里主要关闭。
        os.close();
        inputStream.close();
        j.setMsg("导出成功");
    } catch (Exception e) {  
        e.printStackTrace();  
       // logger.error(ExceptionUtil.getExceptionMessage(e));  
        j.setSuccess(false);
		j.setMsg("导出失败");
    }  
    return j;
} 

/** 
 * 导出Excel 
 * @return 
 *  
 */ 
@RequestMapping(value = "/exportExcelWin")
public AjaxJson exportExcelWin(HttpServletRequest request,HttpServletResponse response){
	AjaxJson j = new AjaxJson();
	response.setCharacterEncoding("utf-8");
	response.setContentType("multipart/form-data");
	String fileName = "摇一摇中奖记录.xls";  
	try {  
		response.setHeader("Content-disposition", "attachment; filename="  
				+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));
		String actId =  ContextHolderUtils.getRequest().getParameter("actId");//返回时的url
		InputStream inputStream = wxActShaketicketRecordService.exportExcelWin(actId); 
		OutputStream os = response.getOutputStream();
		byte[] b = new byte[2048];
		int length;
		while ((length = inputStream.read(b)) > 0) {
			os.write(b, 0, length);
		}
		// 这里主要关闭。
		os.close();
		inputStream.close();
		j.setMsg("导出成功");
	} catch (Exception e) {  
		e.printStackTrace();  
		// logger.error(ExceptionUtil.getExceptionMessage(e));  
		j.setSuccess(false);
		j.setMsg("导出失败");
	}  
	return j;
}  
}

