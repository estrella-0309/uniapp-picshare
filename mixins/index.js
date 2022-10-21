import{Focus,UnFocus,Like,UnLike,Collect,UnCollect,Detail} from "@/api/index/index.js"
import timeFrom from'@/time/timeFrom.js'
let Checked = {
	methods: {
		// 关注
		async ClickFocus(item,userId) {
			if (item.hasFocus) {
				item.hasFocus = false;
				let result=await UnFocus(item.pUserId,userId);
				if(result.code==200){
					uni.showToast({
						title: "取消关注",
						icon: "success",
						duration: 1000,
					});
				}
				else{
					uni.showToast({
						title: result.msg,
						icon: "error",
						duration: 1000,
					});
				}
			} else {
				item.hasFocus = true;
				let result=await Focus(item.pUserId,userId)
				if(result.code==200){
					uni.showToast({
						title: "关注成功",
						icon: "success",
						duration: 1000,
					});
				}
				else{
					uni.showToast({
						title: result.msg,
						icon: "error",
						duration: 1000,
					});
				}
				
			}
		},
		//点赞
		async ClickLike(item,userId) {
			console.log(item)
			if (item.hasLike) {
				item.hasLike = false;
				item.likeNum--;
				let result=await UnLike(item.likeId);
				console.log(result)
				uni.showToast({
					title: "取消点赞",
					icon: "success",
					duration: 1000,
				});
			} else {
				item.hasLike = true;
				item.likeNum++;
				let result=await Like(item.id,userId)
				if(result.code==200){
					let temp=await Detail(item.id,userId);
					item.likeId=temp.data.likeId;
				}
				uni.showToast({
					title: "点赞成功",
					icon: "success",
					duration: 1000,
				});
				
			}
		},
		
		async ClickCollect(item,userId) {
			console.log(item,"item")
			if (item.hasCollect) {
				item.hasCollect = false;
				item.collectNum--;
				let result=await UnCollect(item.collectId);
				console.log(result)
				uni.showToast({
					title: "取消收藏",
					icon: "success",
					duration: 1000,
				});
			} else {
				item.hasCollect = true;
				item.collectNum++;
				let result=await Collect(item.id,userId)
				console.log(result)
				if(result.code==200){
					let temp=await Detail(item.id,userId);
					item.collectId=temp.data.collectId;
				}
				uni.showToast({
					title: "收藏成功",
					icon: "success",
					duration: 1000,
				});
			}
		},
		formatDateTime(timeDate){
		  let t = timeFrom(timeDate, "yyyy年mm月dd日");
		  return t;
		},
		
	}
}
export default Checked
