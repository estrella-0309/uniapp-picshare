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
							<view class="createtime">
								{{formatDateTime(item.createTime)}}发布
							</view>					
				  	</view>		  	
				  </view>
				 
				  <view class="picture" >
					<template v-for="(imgs,index) in item.imageUrlList">
						<image :src="imgs" alt=""  mode="aspectFill" @click.stop="previewImage(item.imageUrlList)"></image>
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
								<image class="iconimg-2" :src="item.hasLike?good:Ungood" @click.stop="ClickLike(item,id)" alt=""></image>
							</view>
							<view class="good">
								<view class="collectnums">
									{{item.collectNum}}
								</view>
								<image class="iconimg-2" :src="item.hasCollect?collect:Uncollect"  @click.stop="ClickCollect(item,id)" alt="">
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
	mixins: [Checked],
			data() {
				return {
					ans:{},
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
				previewImage(imgs) {
				  uni.previewImage({ 
				    urls:imgs,
				  });
				},
				init(){
					uni.request({
						url:'http://47.107.52.7:88/member/photo/share',
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
						url:'http://47.107.52.7:88/member/photo/share',
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
							current:1,
							size:10,
							userId:this.id
						},
						success:res=>{
							if(res.data.code==200){
								this.list.push.apply(this.list,res.data.data.records);
								this.page=1;
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
					width: 100rpx;
					font-size: 20px;
					font-weight: 700;
					position: relative;
					top:-100rpx;
					left:130rpx;
					display: inline-block;
				}
				.createtime{
					font-size: 14px;
					color: #ccc;
					position: relative;
					top:-100rpx;
					left:120rpx;
					display: inline-block;
				}
			}
			.header-right{
				margin-right: 50rpx;
				margin-top: 55rpx;
				
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

