<template>	
	<view class="box">
		<view v-if="!list.length>0">
			<u-empty text="没有内容" mode="list" style="width:750rpx;"></u-empty>
		</view>
			 <view class="list">
			   <view class='list-half'>
			       <view class="item" v-for="(item,index) in list" :key="index" v-if="index%2 !== 0">
			         <image   class='card' :src="item.imageUrlList[0]" style="width: 346rpx;" mode="aspectFill"></image>
					  <u-icon    class="deleteicon" v-if="type.txt=='My'" name="trash"  size="25"  color="#ccc" @click="Delete(item)"></u-icon>	
			       </view> 
			     </view>
			     <view class='list-half' style="margin-left: 15px;" >
			         <view class="item" v-for="(item,index) in list" :key="index" v-if="index%2 == 0">
			           <image class='card' :src="item.imageUrlList[0]" style="width: 346rpx;" mode="aspectFill"></image>
					   <u-icon class="deleteicon" v-if="type.txt=='My'" name="trash"  size="25" color="#ccc"  @click="Delete(item)"></u-icon>	
			         </view>
			       </view>
			     </view>
			   </view>
	</view>
</template>

<script>
	import {mapState} from "vuex"
	import {GetMy,Getlike, GetCollect,DeleteShare} from "@/api/index/index.js"
	import Checked from '@/mixins/index.js'
	export default{			
		mixins: [Checked],
		props:["type"],
		data() {
			 return{
				 list:[],
				 page:1,
			 }
		},
		mounted() {
			this.getData()
		},
		methods:{
			async Delete(item){
				let result=await DeleteShare(item.id,this.userid)
				if(result.code==200){
					this.list=this.list.filter(i =>{
						return i.id!=item.id
					})
				}
			},
			async getData(){
				let result;
				switch(this.type.txt){
					case 'My':
					result=await GetMy(this.page,this.userid);
					break;
					case 'Like':
					result=await Getlike(this.page,this.userid);
					break;
					case 'Collect':
					result=await GetCollect(this.page,this.userid);
					break;
				}			
				if(result.code==200){
					if(result.data.records.length==0){
						uni.showToast({
										title:"数据到底了",
										icon:'error',
										duration:1000,
						})
						this.page--;
					}
					else{
						this.list=[...this.list,...result.data.records]
					}					
				}
				this.page++;
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
	 /deep/ uni-image>img {
		     width: 100%;
		     height: 200px;
		 }
	 .box{
		display: flex;
		flex-wrap: wrap;
		
		.list {
		    padding: 20rpx 0;
		    display: flex;
		    justify-content: space-between;
		  }
		  .list .list-half {
			margin-left: 15rpx;
		    width: 346rpx;
		    height: 100%;
		    box-sizing: border-box;	
			.item{
				position: relative;
				.deleteicon{
					position: absolute;
					bottom:20px;
					right: 10px;
				}
			}
		  }
		  
		  .list-half .card {
		    border-radius: 20rpx;
		    overflow: hidden;
		    margin-bottom: 15rpx;
		  }
		 .title{
			font-size:23px;
		 }
		 .content{
			font-size:16px;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
		 }
		 .icon{
			display: flex;
			margin-top: 10rpx;
			width:100%;
			justify-content: flex-end;
			.like{		 
				flex:1;
			}
			.collect{
				flex:1;
			}
		 }
	 }
</style>