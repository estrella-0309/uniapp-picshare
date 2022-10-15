import api from '../api.js'
//登录
export const Login=async(username,password)=>{
	let result =await api({
		url:'/photo/user/login',
		method:'POST',
		"Content-Type": "application/x-www-form-urlencoded",
		data:{
			username:username,password:password
		},
	})
	return result
}
//注册
export const Register=async(username,password)=>{
	console.log(username,password)
	let result =await api({
		url:'/photo/user/register',
		method:'POST',
		data:{
			username:username,password:password
		},
	})
	return result
}
//收藏页
export const GetCollect=async(current,useId)=>{
	let result =await api({
		url:'/photo/collect',
		method:'GET',
	
		data:{
			current:current,size:10,useId:useId
			
		},
	})
	return result
}
//收藏
export const Collect=async(shareId,useId)=>{
	let result =await api({
		url:'/photo/collect',
		method:'POST',
	"Content-Type": "application/x-www-form-urlencoded",
		data:{
			shareId:shareId,useId:useId
		},
	})
	return result
}
//取消收藏
export const UnCollect=async(collectId)=>{
	let result =await api({
		url:'/photo/collect/cancel',
		method:'POST',
		
		data:{
			collectId:collectId,
		},
	})
	return result
}
//一级评论页
export const FirstComment=async(current,shareId)=>{
	let result =await api({
		url:'/photo/comment/first',
		method:'GET',
		data:{
			current:current,
			shareId:shareId,
			size:10,
		},
	})
	return result
}
//发表一级评论
export const AddFirstComment=async(content,shareId,userId,userName)=>{
	let result =await api({
		url:'/photo/comment/first',
		method:'POST',
		data:{
			content:content,
			shareId:shareId,
			userId:userId,
			userName:userName,
		},
	})
	return result
}
//二级评论页
export const GetSecondComment=async(current,shareId,commentId)=>{
	let result =await api({
		url:'/photo/comment/second',
		method:'GET',
		data:{
			current:current,
			shareId:shareId,
			size:10,
			commentId:commentId,
		},
	})
	return result
}
//二级评论
export const SecondComment=async(content,parentCommentId,parentCommentUserId,replyCommentId,userId,shareId,userName)=>{
	let result =await api({
		url:'/photo/comment/second',
		method:'POST',
		data:{
			content:content,
			parentCommentId:parentCommentId,
			parentCommentUserId:parentCommentUserId,
			replyCommentId:replyCommentId,
			replyCommentUserId:replyCommentUserId,
			shareId:shareId,
			userId:userId,
			userName:userName,
		},
	})
	return result
}
//关注页
export const GetFocus=async(current,userId)=>{
	let result =await api({
		url:'/photo/focus',
		method:'GET',
		data:{
			current:current,
			size:10,
			userId:userId,

		},
	})
	return result
}
//关注
export const Focus=async(focusUserId,userId)=>{
	let result =await api({
		url:'/photo/focus',
		method:'POST',
		data:{
			focusUserId:focusUserId,
			userId:userId,
		},
	})
	return result
}
//取消关注
export const UnFocus=async(focusUserId,userId)=>{
	let result =await api({
		url:'/photo/focus/cancel',
		method:'POST',
		data:{
			focusUserId:focusUserId,
			userId:userId,
		},
	})
	return result
}
//上传文件
export const Upload=async(fileList)=>{
	let result=await new Promise((reslove,reject)=>{
		uni.uploadFile({
			url:'http://47.107.52.7:88/member/photo/image/upload',
			files:fileList,
			fileType:"image",
			header:{
				  "Accept": "application/json, text/plain, */*",
				  // "Content-Type": "multipart/form-data",
				  "appId": "24d8ed2ab0444b048cbd5fcdde289109",
				  "appSecret": "300002f6abcaf485d4cb19de0695a0b049dc0",
			 },
			 success :res =>{
				 reslove(res.data)
			 },
			 fail:err=>{
				 reject(err)
			 }
		})
	})
	return result
}
//点赞列表
export const Getlike=async(current,userId)=>{
	let result =await api({
		url:'/photo/like',
		method:'POST',
		
		data:{
			current:current,
			size:10,
			userId:userId,
		},
	})
	return result
}
//点赞
export const Like=async(shareId,userId)=>{
	let result =await api({
		url:'/photo/like',
		method:'POST',
		"Content-Type": "application/x-www-form-urlencoded",
		data:{
			shareId:shareId,
			userId:userId,
		},
	})
	return result
}
//取消点赞
export const UnLike=async(likeId)=>{
	let result =await api({
		url:'/photo/like/cancel',
		"Content-Type": "application/x-www-form-urlencoded",
		method:'POST',
		data:{
			likeId:likeId,
		},
	})
	return result
}
//获取发现列表
export const GetShare=async(current,userId)=>{
	let result =await api({
		url:'/photo/share',
		method:'GET',
		data:{
			current:current,
			size:10,
			userId:userId,

		},
	})
	return result
}
//新增图文分享
export const AddShare=async(content,imageCode,pUserId,title)=>{
	let result =await api({
		url:'/photo/share/add',
		method:'POST',
		data:{
			content:content,
			imageCode:imageCode,
			pUserId:pUserId,
			title:title,
		},
	})
	return result
}
//将保存状态修改为发布状态
export const Change=async(content,id,imageCode,pUserId,title)=>{
	let result =await api({
		url:'/photo/share/change',
		method:'POST',
		data:{
			content:content,
			id:id,
			imageCode:imageCode,
			pUserId:pUserId,
			title:title,
		},
	})
	return result
}
//删除图文分享
export const DeleteShare=async(shareId,userId)=>{
	let result =await api({
		url:'/photo/share/delete',
		method:'POST',
		data:{
			shareId,shareId,
			userId:userId,
		},
	})
	return result
}
//获取单个图文分享的详情
export const Detail=async(shareId,userId)=>{
	let result =await api({
		url:'/photo/share/detail',
		method:'GET',
		data:{
			shareId,shareId,
			userId:userId,
		},
	})
	return result
}
//获取我的动态图片分享列表
export const GetMy=async(current,userId)=>{
	let result =await api({
		url:'/photo/share/myself',
		method:'GET',
		data:{
			current:current,
			size:10,
			userId:userId,
		},
	})
	return result
}
//获取已保存的图文分享列表
export const GetSave=async(current,userId)=>{
	let result =await api({
		url:'/photo/share/save',
		method:'GET',
		data:{
			current:current,
			size:10,
			userId:userId,
		},
	})
	return result
}
//保存图文分享
export const Sava=async(content,imageCode,pUserId,title)=>{
	let result =await api({
		url:'/photo/share/save',
		method:'POST',
		data:{
			content:content,
			imageCode:imageCode,
			pUserId:pUserId,
			title:title,
		},
	})
	return result
}
//修改用户信息
export const Update=async(avatar,id,introduce,sex,username)=>{
	let result =await api({
		url:'/photo/user/update',
		method:'POST',
		data:{
			avatar:avatar,
			id:id,
			introduce:introduce,
			sex:sex,
			username:username,
		},
	})
	return result
}