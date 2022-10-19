<template>
	<view>
		{{List}}
		<view v-if="List.total==0">
			<u-empty text="还没有评论捏" mode="list"></u-empty>
		</view>
		<view class="comment" v-for="res in List.records" :key="res.id" v-else>
			<view class="left"><u-avatar :text="res.userName.substr(0,1)" size="50"></u-avatar></view>
			<view class="right">
				<view class="top">
					<view class="name">{{ res.userName }}</view>
				</view>
				<view class="content">
				{{ res.content }}
				</view>
			
				<view class="reply-box" v-if="res.secondcommentrecords!=[]">
					<view class="item" v-for="item in res.secondcommentrecords" :key="item.id">
						<view class="username">{{ item.userName }}</view>
						<view class="text">{{ item.content }}</view>
					</view>
					<!-- <view class="all-reply" @tap="toAllReply" v-if="res.secondcommentrecords.length>3">
						共{{ res.secondcommentrecords.length }}条回复
						<u-icon class="more" name="arrow-right" :size="26"></u-icon>
					</view> -->
				</view> 
				<view class="bottom">
					{{ res.createTime.split(' ')[0] }}
					<view class="reply" @click="sendsecondmsg(res)">回复</view>
				</view>
				
			</view>
		</view>
		<view class="footer">
			
		</view>
	</view>
</template>

<script>
export default {
	props:['commentList'],
	data() {
		return {
			List:[],
		};
	},
	mounted() {
		console.log(JSON.parse(JSON.stringify(this.commentList)),"comment")
		console.log(this.List)
	},
	updated(){
		console.log(this.List,"update")
	},
	watch:{
		commentList:{
			handler(newVal,oldVal){
				console.log(newVal,"new",oldVal,"old");
				this.List=newVal;
			},
			deep:true,
			immediate: true
		}
	},
	methods: {
		sendsecondmsg(data){
			uni.$emit('sendsecondmsg',{
				type:"second",
				data:data,
			})
		},
	}
};
</script>

<style lang="scss" scoped>
.footer{
	width: 750rpx;
	height: 200rpx;
	// background-color: red;
}
.comment {
	display: flex;
	padding: 30rpx;
	font-size: 20px;
	border-bottom: 1px solid #5677fc;
	.left {
		image {
			width: 64rpx;
			height: 64rpx;
			border-radius: 50%;
			background-color: #f2f2f2;
		}
	}
	.right {
		flex: 1;
		padding-left: 20rpx;

		.top {
			display: flex;
			justify-content: space-between;
			align-items: center;
			margin-bottom: 10rpx;
			.name {
				font-size: 25px;
				color: #5677fc;
			}
			.like {
				display: flex;
				align-items: center;
				color: #9a9a9a;
				font-size: 26px;
				.num {
					margin-right: 4rpx;
					color: #9a9a9a;
				}
			}
			.highlight {
				color: #5677fc;
				.num {
					color: #5677fc;
				}
			}
		}
		.content {
			margin-bottom: 10rpx;
		}
		.reply-box {
			background-color: rgb(242, 242, 242);
			border-radius: 12rpx;
			.item {
				padding: 20rpx;
				border-bottom: solid 2rpx $u-border-color;
				.username {
					font-size: 24px;
					color: #999999;
				}
			}
			.all-reply {
				padding: 20rpx;
				display: flex;
				color: #5677fc;
				align-items: center;
				.more {
					margin-left: 6rpx;
				}
			}
		}
		.bottom {
			margin-top: 20rpx;
			display: flex;
			font-size: 24px;
			color: #9a9a9a;
			.reply {
				color: #5677fc;
				margin-left: 10rpx;
			}
		}
	}
	
}
</style>
