import{Focus,UnFocus,Like,UnLike,Collect,UnCollect} from "@/api/index/index.js"
let Checked = {
	methods: {
		// 关注
		async ClickFocus(item,userId) {
			if (item.hasFocus) {
				item.hasFocus = false;
				let result=await Focus(item.pUserId,userId);
				console.log(result)
				uni.showToast({
					title: "取消点赞",
					icon: "success",
					duration: 1000,
				});
			} else {
				item.hasFocus = true;
				let result=await UnFocus(item.pUserId,userId)
				console.log(result)
				uni.showToast({
					title: "点赞成功",
					icon: "success",
					duration: 1000,
				});
			}
			// uni.$emit('indexFeedCheckChange',item)
			// uni.$emit('myFeedLoveChange',item)
		},
		//点赞
		async ClickLike(item,userId) {
			if (item.hasLike) {
				item.hasLike = false;
				item.likeNum--;
				let result=await Like(item.likeId);
				console.log(result)
				uni.showToast({
					title: "取消点赞",
					icon: "success",
					duration: 1000,
				});
			} else {
				item.hasLike = true;
				let result=await UnLike(item.id,userId)
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
			if (item.hasCollect) {
				item.hasCollect = false;
				item.likeNum--;
				let result=await Like(item.collectId);
				console.log(result)
				uni.showToast({
					title: "取消收藏",
					icon: "success",
					duration: 1000,
				});
			} else {
				item.hasCollect = true;
				let result=await UnLike(item.id,userId)
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
		
		
	}
}
export default Checked
