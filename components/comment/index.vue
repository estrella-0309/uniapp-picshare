<template>
	<view>
		<view v-if="List.total==0">
			<u-empty text="还没有评论捏" mode="list"></u-empty>
		</view>
		<view class="comment" v-for="res in List.records" :key="res.id" v-else>
			<view class="left"><u-avatar :text="res.userName.substr(0,1)" size="30"></u-avatar></view>
			<view class="right">
				<view class="top">
					<view class="name">{{ res.userName }}</view>
				</view>
				<view class="content">
				{{ res.content }}
				</view>
			
				<view class="reply-box" v-if="res.secondcommentrecords">
					<view class="item" v-for="item in res.secondcommentrecords.records" :key="item.id">
						<view class="username">{{ item.userName }}</view>
						<view class="text">{{ item.content }}</view>
					</view>

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
import {GetSecondComment} from "@/api/index/index.js"
export default {

	props:['commentList','id'],
	data() {
		return {
			List:[],
		};
	},
	mounted() {
		console.log(JSON.parse(JSON.stringify(this.commentList)),"comment")
		console.log(this.List)
	},
	watch:{
		commentList:{
			async handler(newVal,oldVal){
				console.log(newVal,"new",oldVal,"old");
				for(let i=0;i<newVal.records.length;i++){
					console.log(this.id,newVal.records[i].id);
					let result=await GetSecondComment(1,this.id,newVal.records[i].id);
					if(result.data!==null){
						newVal.records[i].secondcommentrecords=result.data
					}
				}
				this.List=newVal;
				console.log(this.List)
			},
			deep:true,
			// immediate: true
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
	font-size: 15px;
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
				font-size: 20px;
				color: #5677fc;
			}
			.like {
				display: flex;
				align-items: center;
				color: #9a9a9a;
				font-size: 20px;
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
					font-size: 18px;
					color: #999999;
				}
			}
			.all-reply {
				padding: 12rpx;
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
			font-size: 14px;
			color: #9a9a9a;
			.reply {
				color: #5677fc;
				margin-left: 10rpx;
			}
		}
	}
	
}
</style>
