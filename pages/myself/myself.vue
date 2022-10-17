<template>
	<view class="myself">
		<view>
			<view class="header">
				<view class="information">
					<view class="basis">
						<view class="basis-left">
							<view class="avator">
								<img src="static/bird.jpg" alt="">
							</view>
							<view class="username">
								estrella
							</view>
							<view class="id">
								id:12313123
							</view>
							<view class="sex">
								<view class="text">男</view>
							</view>
							  
							<view class="create">
								<view class="text">
									2022-10-11
								</view>
							</view>
						</view>
					</view>
					<view class="introduce">
						这个人很懒，什么都没有留下
					</view>
					<view class="mylists">
						<view class="focus">
							<view class="num">
								0
							</view>
							<view >
							关注
							</view>
						</view>
						<view class="like">
							<view class="num">
								0
							</view>
							<view >
							喜欢
							</view>
						</view>
						<view class="collections">
							<view class="num">
								0
							</view>
							<view >
							收藏
							</view>
						</view>
						<view class="setting"> 	
							<view class="text">
								编辑资料
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		 <div class="footer">
			 <scroll-view class="scroll1" scroll-x="true">
			 			<view :class="currentTab==index ? 'select' : 'select1'" :data-current="index" @click="swichNav"
			 				v-for="(item,index) in scoll" :key='index'>{{item.txt}}
			 			</view>
			 		</scroll-view>
			 		<swiper :current="currentTab" @change="bindChange" class='swp' :style="{height:aheight?aheight+'px':'auto'}">
			 			<swiper-item>
			 				<view class="list-item">
			 					<view class="list" v-for="(item,index) in list" :key='index'>
			 						<view class="list-img">
			 							<image src="/static/zatu/1.jpg" mode=""></image>
			 						</view>
			 						<view class="list-con">
			 							<view>{{item.title}}</view>
			 							<view class="list-foot">
			 								<view>
			 									<image src="/static/logo.png" mode="" class="user"></image>
			 									<view class="username">{{item.user}}</view>
			 								</view>
			 								<view>
			 									<image src="/static/logo.png" mode="" class="like"></image>
			 									<view class="likenum">{{item.like}}</view>
			 								</view>
			 							</view>
			 						</view>
			 					</view>
			 				</view>
			 			</swiper-item>
			 			<swiper-item>直播间</swiper-item>
			 			<swiper-item>代言福利
			 				<view>
			 					
			 				</view>
			 			</swiper-item>
			 			<swiper-item>KPI</swiper-item>
			 			<!-- <swiper-item>鼻部</swiper-item>
			 			<swiper-item>瘦身塑型</swiper-item> -->
			 		</swiper>
		 </div>
	</view>
	
</template>

<script>
	export default {
		data() {
			return {
				currentTab: 0,
								aheight: '',
								scoll: [{
									txt: '精选'
								}, {
									txt: '直播间'
								}, {
									txt: '代言福利'
								}, {
									txt: 'KPI'
								}],
								list: [{
										user: '1',
										title: 'ssssssssss',
										like: '33'
									}, {
										user: '2',
										title: 'ssaassssssss',
										like: '33'
									},
									{
										user: '3',
										title: 'ss2ssssssss',
										like: '33'
									},
									{
										user: '4',
										title: 'ss2ssssssss',
										like: '33'
									},
									{
										user: '5',
										title: 'ss2ssssssss',
										like: '33'
									},
									{
										user: '6',
										title: 'ss2ssssssss',
										like: '33'
									}
								]
			}
		},
		onShow() {
					// 动态获取滑动页面高度
					const query = uni.createSelectorQuery().in(this);
					query.select('.list-item').boundingClientRect(data => {
						this.aheight = data.height
					}).exec();
				},
		methods: {
			bindChange: function(e) {
							this.currentTab = e.detail.current
						},
						//点击tab切换
						swichNav: function(e) {
							var that = this;
							if (this.currentTab === e.target.dataset.current) {
								return false;
							} else {
								this.currentTab = e.target.dataset.current
							}
						}
		}
	}
</script>

<style lang="scss" scoped>
	.scroll1 {
			position: fixed;
			top: 0rpx;
			z-index:99;
			width: 100%;
			white-space: nowrap;
			padding: 20rpx 20rpx;
			background-color: #ffffff;
	 
			& view {
				white-space: normal;
				display: inline-block;
			}
	 
			& view:before {
				content: '|';
				color: #f4f4f4;
				margin: 0 20rpx;
			}
	 
			& view:first-child:before {
				display: none;
			}
	 
			.select {
				color: #fb6583;
				font-size: 40rpx;
			}
	 
			.select1 {
				color: #808080;
			}
		}
	 
		.swp {
			margin-top: 90rpx;
			.list-item {
				overflow: hidden;
				// margin-left: -1%;
	 
				.list {
					float: left;
					width: 48%;
					margin: 20rpx 0 0 1%;
					border: solid 1px #eaeaea;
					background-color: #ffffff;
					border-radius: 12rpx;
	 
					.list-img {
						image {
							object-fit: fill;
							width: 100%;
							border-top-left-radius: 12rpx;
							border-top-right-radius: 12rpx;
							height: 364rpx;
						}
					}
	 
					.list-con {
						padding: 15rpx;
	 
						.list-foot {
							margin-top: 20rpx;
							display: flex;
							justify-content: space-between;
							align-items: center;
	 
							&>view {
								font-size: 26rpx;
								display: flex;
								align-items: center;
							}
	 
							.username {
								color: #999999;
								margin-left: 10rpx;
								width: 150rpx;
								overflow: hidden;
								text-overflow: ellipsis;
								white-space: nowrap;
							}
	 
							.user {
								width: 50rpx;
								height: 50rpx;
							}
	 
							.likenum {
								color: #333333;
								margin-left: 10rpx;
							}
	 
							.like {
								width: 28rpx;
								height: 28rpx;
							}
						}
					}
				}
			}
		}
	.text{
		width: 80%;
		height: 40%;
		border-radius: 40px;
		display: flex;
		justify-content: center;
		align-items: center;
		color: #fff;
		background-color:rgba(255, 255, 255, 0.5);
	}
	.myself{
		
		box-sizing: border-box;
		.header{
			background-color: #555360;
			width: 750rpx;
			height: 600rpx;
		
			position: absolute;
			.information{
				margin-top:70rpx;
				width: 750rpx;
				height: 680rpx;
				
				.basis{
					width: 750rpx;
					height: 250rpx;
				
					position: absolute;
					.basis-left{
				
						width: 500rpx;
						height: 200rpx;
						margin: 25rpx 30rpx;
						position: absolute;
						margin-left: 20rpx;
						.avator{
							position: relative;
							top:40rpx;
					
							width: 150rpx;
							height: 150rpx;
							img{
								width: inherit;
								border-radius:50% ;
							}
						}
						.username{
							position: relative;
							top:-120rpx;
							left:190rpx;
							width: 150rpx;
							height: 50rpx;
							color: #fff;
							font-weight: 700;
							font-size: 20px;
						}
						.id{
							position: relative;
							top:-115rpx;
							left:190rpx;
							font-size: 12px;
							color: #ccc;
						}
						.sex{
							position: relative;
							top:-95rpx;
							left:190rpx;
							color: #fff;
							width: 80rpx;
						
						}
						.create{
							position: relative;
							top: -68px;
							left: 280rpx;
							width: 150px;
							color: #fff;
						}
					}
					
				}				
				.introduce{
					position: relative;
					top: 280rpx;
					margin-left: 40rpx;
					color: #fff;
				}
				.mylists{
					position: relative;
					top: 350rpx;
					display:flex;
					view{
						color: #fff;
						text-align: center;
						flex:1.1;
					}
					view:nth-child(3){
						flex: 1.5;
					}
					.setting{
						flex:2;
						height: 200rpx;
						
					}
				}
			}
		}
		.footer{
			width: 750rpx;
			position: relative;
			top: 600rpx;
		}
	}
</style>
