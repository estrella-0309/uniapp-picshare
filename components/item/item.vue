<template>
	
	<view class="box">
		<view class="list" v-for="item in list.records" :key='item.id'>
		
			<view class="list-img">
				<u-image :src="item.imageUrlList[0]" width="100%" ></u-image>
				<view class="list-con">
					<view class="list-foot">
						<view>					
							<view class="username">{{item.username}}</view>
						</view>
							<view class="content">{{item.content}}</view>
							<div class="icon">
								<u-icon   :name="item.hasCollect?'star-fill':'star'"  label-size="10"class="collect"></u-icon>
								
								<u-icon   :name="item.hasLike?'thumb-up-fill':'thumb-up'"  size="20"  label-size="10" class="like"></u-icon>
								</div>
						</view>
					</view>
				</view>
			</view>
			
		</view>
		</view>
	</view>
		
</template>

<script>
	import {mapState} from "vuex"
	import {GetMy,Getlike, GetCollect} from "@/api/index/index.js"
	export default{
		props:["type"],
		data() {
			 return{
				 list:[1,2,34],
				 page:1,
			 }
		},
		mounted() {
			this.getData()
		},
		methods:{
			async getData(){
				let result;
				console.log(this.type)
				switch(this.type.txt){
					
					case 'My':
					result=await GetMy(this.page,this.userid);
					console.log(result)
					if(result.code==200){
						this.list=result.data;
					}
					break;
					case 'Like':
					result=await Getlike(this.page,this.userid);
					console.log(result);
					if(result.code==200){
						console.log(this.list)
						this.list=result.data;
						console.log(this.list,"code")
					}
					break;
					case 'Collect':
					result=await GetCollect(this.page,this.userid);
					console.log(result,"co");
					if(result.code==200){
						this.list=result.data;
						console.log(this.list,"collect")
					}
					break;
				}
				
			}
		},
		computed: {
			...mapState({
				userid:state=>state.user.id,
				username:state=>state.user.username,
			}),
		},
	}
</script>

	 
 <style lang="scss" scoped>
	 .box{
		 display: flex;
		  flex-wrap: wrap;
		 .list{
			  background-color:#DFE0EF ;
		 		 // background-color: blue;
		 		 margin-right:13rpx;
				 margin-bottom: 20rpx;
				 // border:1px solid black;
		 		 width:49%;
				 // background-color: #fff;
				 border-radius:10px ;
				 height: 600rpx;
		 }
		 .list:nth-child(even){
			 margin-right:0;
		 }
		 .username{
			 font-size:20px;
		 }
		 .content{
				overflow: hidden;
			    text-overflow: ellipsis;
			    white-space: nowrap;
		 }
		 .icon{
			 float: right;
			 margin-top: 10rpx;
			 .like{		 
				 float: right;
			 }
			 .collect{
				 float: right;
			 }
		 }
	 }
	 
// .list-item {
// 		display:flex;
// 		flex-wrap: wrap;  flex-direction: row;
// 		// overflow: hidden; 
// 		&>
// 		.list {
// 				// float: left;
// 				flex:1;
// 				width: 48%;
// 				margin: 20rpx 0 0 1%;
// 				border: solid 1px #eaeaea;
// 				background-color: #ffffff;
// 				border-radius: 12rpx;
		 
// 				.list-img {
// 					image {
// 						object-fit: fill;
// 						width: 100%;
// 						border-top-left-radius: 12rpx;
// 						border-top-right-radius: 12rpx;
// 						height: 364rpx;
// 					}
// 				}
		 
// 				.list-con {
// 					padding: 15rpx;
		 
// 					.list-foot {
// 						margin-top: 20rpx;
// 						display: flex;
// 						justify-content: space-between;
// 						align-items: center;
		 
// 						&>view {
// 							font-size: 26rpx;
// 							display: flex;
// 							align-items: center;
// 						}
		 
// 						.username {
// 							color: #999999;
// 							margin-left: 10rpx;
// 							width: 150rpx;
// 							overflow: hidden;
// 							text-overflow: ellipsis;
// 							white-space: nowrap;
// 						}
		 
// 						.user {
// 							width: 50rpx;
// 							height: 50rpx;
// 						}
		 
// 						.likenum {
// 							color: #333333;
// 							margin-left: 10rpx;
// 						}
		 
// 						.like {
// 							width: 28rpx;
// 							height: 28rpx;
// 						}
// 					}
// 				}
// 			}
// 		}
</style>