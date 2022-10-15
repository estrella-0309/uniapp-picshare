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
		 </div>
	</view>
	
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			
		}
	}
</script>

<style lang="scss" scoped>
	
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
							top: -70px;
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
