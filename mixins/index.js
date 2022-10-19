import{Focus,UnFocus,Like,UnLike,Collect,UnCollect} from "@/api/index/index.js"
import timeFrom from'@/time/timeFrom.js'
let Checked = {
	methods: {
		// 关注
		async ClickFocus(item,userId) {
			if (item.hasFocus) {
				item.hasFocus = false;
				let result=await UnFocus(item.pUserId,userId);
				console.log(result)
				uni.showToast({
					title: "取消关注",
					icon: "success",
					duration: 1000,
				});
			} else {
				item.hasFocus = true;
				let result=await Focus(item.pUserId,userId)
				console.log(result)
				uni.showToast({
					title: "关注成功",
					icon: "success",
					duration: 1000,
				});
			}
			// uni.$emit('indexFeedCheckChange',item)
			// uni.$emit('myFeedLoveChange',item)
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
				console.log(result)
				uni.showToast({
					title: "点赞成功",
					icon: "success",
					duration: 1000,
				});
			}
			// uni.$emit('indexFeedLoveChange',item)
			// uni.$emit('myFeedLoveChange',item)
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
				uni.showToast({
					title: "收藏成功",
					icon: "success",
					duration: 1000,
				});
			}
			// uni.$emit('indexFeedLoveChange',item)
			// uni.$emit('myFeedLoveChange',item)
		},
		formatDateTime(timeDate){
		  // let Time = new Date(timeDate);
		  // console.log(Time,"Time");
		  // let timestemp = Time.getTime();
		  // console.log(timestemp,"timestemp");
		  let t = timeFrom(timeDate, "yyyy年mm月dd日");
		  return t;
		},
		formatDateTime2(timeDate){
		  // let Time = new Date(timeDate);
		  // console.log(Time,"Time");
		  // let timestemp = Time.getTime();
		  // console.log(timestemp,"timestemp");
		  let t = timeFrom(timeDate, "yyyy-mm-dd");
		  return t;
		}
		
	}
}
export default Checked
