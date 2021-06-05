import React from 'react'
import { Avatar } from '@material-ui/core'
import './ChannelHeader.css'
function ChannelHeader({ name, description, urlPic }) {
  return (
    <div className='channelHeader'>
      <div className='channelHeader_body'>
        <div className='channelHeader_name'>
          <h1>{name}</h1>
          <div className='channelHeader_avatar'>
            <Avatar src={urlPic}>{name[0]}</Avatar>
          </div>
        </div>
        <p>{description}</p>
      </div>
    </div>
  )
}

export default ChannelHeader
