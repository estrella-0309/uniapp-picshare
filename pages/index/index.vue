<template>
	<view class="app">
				<view class="index-list" 
					v-for="item in list"
				  :key="item.id">
				  <view class="header">
				  	<view class="header-left" >
						<view class="avator" >	
							<u-avatar :text="item.username.substr(0,1)" size="50"></u-avatar>
						</view>
				  			<view class="username">{{item.username}}</view>						
				  	</view>
				<!--  	<view class="header-right" @click="attention(item)">
				  		<view>关注</view>
				  		<image class="iconimg" :src="item.hasFocus?checked:Unchecked" alt="" style="width: 40px;" mode="scaleToFill"></image>
				  	</view>	 -->		  	
				  </view>
				 
				  <view class="picture" >
					<template v-for="imgs in item.imageUrlList">
						<image :src="imgs" alt=""  mode="aspectFill"></image>
					</template> 
				  </view>
				  <view class="footer">
					 <view class="footer-left">
					 	<view class="title">
					 		{{item.title}}
					 	</view>
						<view class="content">
							{{item.content}}
						</view>
					  </view>
					 
					  	
						<view class="goodandcollect">
							<view class="good">
								<view class="goodnums">
									{{item.likeNum}}
								</view>
								<image class="iconimg-2" :src="item.hasLike?good:Ungood" @click="GetLike(item)" alt=""></image>
							</view>
							<view class="good">
								<view class="collectnums">
									{{item.collectNum}}
								</view>
								<image class="iconimg-2" :src="item.hasCollect?collect:Uncollect" @click="GetCollect(item)" alt="">
							</view>
						</view>
						
				  </view>
				  </view>
		</view>
	
</template>

<script>
import {mapState} from "vuex"
import Unchecked from '@/static/checked.png'
import Ungood from '@/static/good.png'
import good from '@/static/good-h.png'
import checked from '@/static/checked-h.png'
import Uncollect from '@/static/collect.png'
import collect from '@/static/collect-h.png'
import Checked from '@/mixins/index.js'
export default {
			// mixins: [feedMixin],
			data() {
				return {
					text:"无",
					Unchecked:Unchecked,
					checked:checked,
					Uncollect:Uncollect,
					collect:collect,
					Ungood:Ungood,
					good:good,
					page:1,					
					list:[],
				}
			},
			onLoad(option) {					
				uni.startPullDownRefresh();
			},
			onPullDownRefresh() {
				this.init();
					setTimeout(function () {
						uni.stopPullDownRefresh();
					}, 1000);
			},
			onReachBottom(){
				this.getData();
			},
			methods: {	
				detail(shareid){
					console.log(shareid,"shareid",this.id)
					uni.request({
						url:'http://47.107.52.7:88/member/photo/share/detail',
						method:'GET',
						header:{
							"Accept": "application/json, text/plain, */*",
							"Content-Type": "application/x-www-form-urlencoded",
							"appId": "24d8ed2ab0444b048cbd5fcdde289109",
							// "appId": "d39fc189485c43d9a4b37463b238ac84",
							"appSecret": "300002f6abcaf485d4cb19de0695a0b049dc0",
							// "appSecret": "06219a004b5ecf6c84f89ba5f9d5c81a037f
						},
						data:{
							shareId:shareid,
							userId:this.id,
						},
						success:res=>{
							if(res.data.code==200){
								this.ans=res.data.data
							}
						}
					})
					console.log(ans,"data");
					return ans;
				},
				attention(item){
					//取消关注用户
					if(item.hasFocus){
						uni.request({
							url:'http://47.107.52.7:88/member/photo/focus/cancel',
							method:'POST',
							header:{
								  "Accept": "application/json, text/plain, */*",
								  "Content-Type": "application/x-www-form-urlencoded",
								  "appId": "24d8ed2ab0444b048cbd5fcdde289109",
								  // "appId": "d39fc189485c43d9a4b37463b238ac84",
								  "appSecret": "300002f6abcaf485d4cb19de0695a0b049dc0",
								  // "appSecret": "06219a004b5ecf6c84f89ba5f9d5c81a037f6"
							},
							data:{
								focusUserId:item.pUserId,
								userId:this.id,
							},
							success:res =>{
								if(res.data.code==200){
									item.hasFocus=!item.hasFocus;
									uni.showToast({
													title:'取消关注成功',
													icon:'error',
													duration:1000,
									})
								}
								
							}
						})
					}
					//关注该用户
					else{
						uni.request({
							url:'http://47.107.52.7:88/member/photo/focus',
							method:'POST',
							header:{
								  "Accept": "application/json, text/plain, */*",
								  "Content-Type": "application/x-www-form-urlencoded",
								"appId": "24d8ed2ab0444b048cbd5fcdde289109",
								"appSecret": "300002f6abcaf485d4cb19de0695a0b049dc0",
								  // "appId": "d39fc189485c43d9a4b37463b238ac84",
								  // "appSecret": "06219a004b5ecf6c84f89ba5f9d5c81a037f6"
							},
							data:{
								focusUserId:item.pUserId,
								userId:this.id,
							},
							success:res =>{
								if(res.data.code==200){
									item.hasFocus=!item.hasFocus;
									uni.showToast({
													title:"关注成功",
													icon:'success',
													duration:1000,
									})
								}
								
							}
							
						})
					}
					
				},
				GetLike(item){
					//取消点赞
					if(item.hasLike){
						uni.request({
							url:'http://47.107.52.7:88/member/photo/like/cancel',
							method:'POST',
							header:{
								  "Accept": "application/json, text/plain, */*",
								  "Content-Type": "application/x-www-form-urlencoded",
								  "appId": "24d8ed2ab0444b048cbd5fcdde289109",
								  // "appId": "d39fc189485c43d9a4b37463b238ac84",
								  "appSecret": "300002f6abcaf485d4cb19de0695a0b049dc0",
								  // "appSecret": "06219a004b5ecf6c84f89ba5f9d5c81a037f6"
							},
							data:{
								likeId:item.likeId
							},
							success:res =>{
								if(res.data.code==200){
									item.hasLike=!item.hasLike;
									item.likeNum--;
									uni.showToast({
													title:'取消点赞成功',
													icon:'error',
													duration:1000,
									})
									
								}
								
							}
						})
					}
					//点赞
					else{
						uni.request({
							url:'http://47.107.52.7:88/member/photo/like',
							method:'POST',
							header:{
								  "Accept": "application/json, text/plain, */*",
								  "Content-Type": "application/x-www-form-urlencoded",
								"appId": "24d8ed2ab0444b048cbd5fcdde289109",
								"appSecret": "300002f6abcaf485d4cb19de0695a0b049dc0",
								  // "appId": "d39fc189485c43d9a4b37463b238ac84",
								  // "appSecret": "06219a004b5ecf6c84f89ba5f9d5c81a037f6"
							},
							data:{
								shareId:item.id,
								userId:this.id,
							},
							success:res =>{
								if(res.data.code==200){
									console.log(this.detail(item.id))
									uni.showToast({
													title:"点赞成功",
													icon:'success',
													duration:1000,
									})
								
								}
								
							}
							
						})
					}
					
				},
				GetCollect(item){
					//取消收藏
					if(item.hasCollect){
					
						uni.request({
							url:'http://47.107.52.7:88/member/photo/collect/cancel',
							method:'POST',
							header:{
								  "Accept": "application/json, text/plain, */*",
								  "Content-Type": "application/x-www-form-urlencoded",
								  "appId": "24d8ed2ab0444b048cbd5fcdde289109",
								  // "appId": "d39fc189485c43d9a4b37463b238ac84",
								  "appSecret": "300002f6abcaf485d4cb19de0695a0b049dc0",
								  // "appSecret": "06219a004b5ecf6c84f89ba5f9d5c81a037f6"
							},
							data:{
								collectId:item.collectId
							},
							success:res =>{
								if(res.data.code==200){
									item.hasCollect=!item.hasCollect;
									uni.showToast({
													title:'取消收藏成功',
													icon:'error',
													duration:1000,
									})
								}
								
							}
						})
					}
					//收藏
					else{
						console.log(item,"shoucang")
						uni.request({
							url:'http://47.107.52.7:88/member/photo/collect',
							method:'POST',
							header:{
								  "Accept": "application/json, text/plain, */*",
								  "Content-Type": "application/x-www-form-urlencoded",
								"appId": "24d8ed2ab0444b048cbd5fcdde289109",
								"appSecret": "300002f6abcaf485d4cb19de0695a0b049dc0",
								  // "appId": "d39fc189485c43d9a4b37463b238ac84",
								  // "appSecret": "06219a004b5ecf6c84f89ba5f9d5c81a037f6"
							},
							data:{
								shareId:item.id,
								userId:this.id,
							},
							success:res =>{
								if(res.data.code==200){
									item.hasCollect=!item.hasCollect;
									uni.showToast({
													title:"收藏成功",
													icon:'success',
													duration:1000,
									})
								}
								
							}
							
						})
					}
					
				},
				init(){
					uni.request({
						url:'http://47.107.52.7:88/member/photo/focus',
						method:'GET',
						header:{
							  // "Accept": "application/json, text/plain, */*",
							  // "Content-Type": "application/x-www-form-urlencoded",
							  "appId": "24d8ed2ab0444b048cbd5fcdde289109",
							  "appSecret": "300002f6abcaf485d4cb19de0695a0b049dc0",
							  // "appId": "d39fc189485c43d9a4b37463b238ac84",
							  // "appSecret": "06219a004b5ecf6c84f89ba5f9d5c81a037f6"
						},
						data:{
							current:this.page,
							size:10,
							userId:this.id
						},
						success:res=>{
							 if(res.data.code==200){
									console.log(res.data.data.records,"focus")				
									this.list=res.data.data.records;
									this.page++;
								
							} 
							else{
								uni.showToast({
												title:res.data.msg,
												icon:'error',
												duration:1000,
								})
							}
						}
					})
				},
				getData(){
					uni.request({
						url:'http://47.107.52.7:88/member/photo/focus',
						method:'GET',
						header:{
							  "Accept": "application/json, text/plain, */*",
							  "Content-Type": "application/x-www-form-urlencoded",
							  // "appId": "24d8ed2ab0444b048cbd5fcdde289109",
							    // "appSecret": "300002f6abcaf485d4cb19de0695a0b049dc0",
							  "appId": "d39fc189485c43d9a4b37463b238ac84",
							  "appSecret": "06219a004b5ecf6c84f89ba5f9d5c81a037f6"
						},
						data:{
							current:this.page,
							size:10,
							userId:this.id
						},
						success:res=>{
							if(res.data.code==200){
								this.list.push.apply(this.list,res.data.data.records);
								this.page++;
							}
							else{
								uni.showToast({
											title:res.data.msg,
											icon:'error',
											duration:1000,
								})
							}
						}
					})
				}
			},
			computed:{
				...mapState({
					id:state=>state.user.id,
					username:state=>state.user.username,
					appKey:state=>state.user.appKey,
					password:state=>state.user.password,
					sex:state=>state.user.sex,
					introduce:state=>state.user.introduce,
					avatar:state=>state.user.avatar,
					createTime:state=>state.user.createTime,
					lastUpdateTime:state=>state.user.lastUpdateTime
				}),
			}
		}
</script>

<style scoped lang="scss">
	/deep/ .u-text{
		span{
			margin-top: -10rpx;
		}
		
	}
.iconimg{
	margin-top: 50rpx;
	width: 80rpx;
	height:80rpx;
}
.iconimg-2{
	margin-top: 12rpx;
	width:60rpx;
	height:60rpx;
}

.app{
	float: left;
	background-color: #f0f0f0;
	.index-list{
		margin-bottom: 100rpx;
		background-color: #fff;
		float: left;
		width: 750rpx;
		.header{
			box-sizing: border-box;
			width: 750rpx;
			height: 150rpx;
			.header-left{
				float: left;
				width: 300rpx;
				padding: 30rpx 30rpx;
				position: absolute;
				.avator{
					// position: relative;
					// width: 100rpx;
					color: red;
					span {
						
					}
				
				}
				.username{
					font-size: 20px;
					font-weight: 700;
					position: relative;
					top:-85rpx;
					left:140rpx;
					display: inline-block;
	
				}
			}
			.header-right{
				margin-right: 50rpx;
				view{
					margin-top: 60rpx;
					margin-right: 10rpx;
					float: left
				}
				img{
					margin-top: 40rpx;
					float: right;
				}
				float: right;
				
			}
		}
		.picture{
			float: left;
			width: 730rpx;
			margin: 0 35rpx;
			// display:flex;
			flex-wrap: wrap;
			image{
				// flex:1;
				margin-right: 20rpx;
				max-width: 210rpx;
				max-height: 210rpx;
				
			}
		}
		.footer{
			float: left;
			width: 750rpx;
			margin-top: 20rpx;
			padding-left: 35rpx;
			.footer-left{
				float: left;
			}
			.title{
				font-size: 20px;
				font-weight: 700;
			}
			.content{
				padding-top: 20rpx;
				width: 700rpx;
				color: #8c8c8c;
			}
			
			.goodandcollect{
				float: right;
				margin-right: 60rpx;
				.good{
					float: left;
					margin-left: 30rpx;
					.goodnums{
						float: left;
						margin-top: 26rpx;
						margin-right: 10rpx;
					}
					.collectnums{
						float: left;
						margin-top: 26rpx;
						margin-right: 10rpx;
					}
				}
				
			}
		}
	}
	
	
}
</style>

