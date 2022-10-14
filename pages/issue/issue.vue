<template>
	<view class="box">
		<div class="summit">
			<u-button type="primary" ripple="true" ripple-bg-color="red">提交</u-button>
			</div>
		<view class="msg">
			<view class="title">
				标题：
				<el-input
				  placeholder="请输入内容"
				  v-model="title"
				  clearable 
				  style="width: 80%;">
				</el-input>
			</view>
			<view class="content">
				内容：
				<el-input
				  placeholder="请输入内容"
				  v-model="content"
				  clearable
				   style="width: 80%;">
				</el-input>
			</view>
		</view>
		<view class="images">
			<view class="images-title">
				上传照片（不能超过9张）
			</view>
			<el-upload
			  action="#"
			  list-type="picture-card"
			  :auto-upload="false"
			  :limit="9"
			 
			  :on-success="(response, file, fileList)=>{return onSuccess(response, file, fileList, index)}"
			  >
			    <i slot="default" class="el-icon-plus"></i>
			    <div slot="file" slot-scope="{file}">
			      <img
			        class="el-upload-list__item-thumbnail"
			        :src="file.url" alt=""
			      >
			      <span class="el-upload-list__item-actions">
			        <span
			          v-if="!disabled"
			          class="el-upload-list__item-delete"
			          @click="handleRemove(file)"
			        >
			          <i class="el-icon-delete"></i>
			        </span>
			      </span>
			    </div>
			</el-upload>
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				disabled: false,
				title:'',
				content:'',
				formList: [{ pics: [] }]
			}
		},
		methods: {
			 onSuccess(response, file, fileList, idx) {
			      // 这里是element的上传地址，对应的name,url,自己打印fileList对照
				  console.log(fileList,this.formList);
			      this.formList[idx].pics.push({ name: file.name, url: file.url });
			    },
		}
	}
</script>

<style>
	
</style>
<style lang="scss" scoped>
/deep/ .el-upload--picture-card{
	width: 200rpx;
	height: 200rpx;
	
	i{
		position: relative;
		top: -40rpx;
	}
}
/deep/ .el-upload-list__item  {
	  width: 200rpx;
	  height: 200rpx;
	  margin-right: 31rpx;
	}
	.box{
		.summit{
			width: 750rpx;
			position: fixed;
			bottom: 100rpx;
			
		}
		.msg {
			margin:50rpx 20rpx;
			.title{
				margin-bottom: 50rpx;
			}
		}
		.images{
			.images-title{
				margin-bottom: 20rpx;
				color: #ccc;
			}
			img{
				
				width: 200rpx;
				height: 200rpx;
			}
			margin: 50rpx 20rpx;
		}
	}
</style>
