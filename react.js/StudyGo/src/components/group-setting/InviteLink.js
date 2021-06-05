import React, { useState } from 'react'
import '../sidebar/AddChannelPopUp.css'
import ReactDom from 'react-dom'
import { useSelector } from 'react-redux'
import { selectGroupId } from '../../features/appSlice'

const MODAL_STYLES = {
 position: 'fixed',
 top: '50%',
 left: '50%',
 transform: 'translate(-50%, -50%)',
 zIndex: 1000
}

const OVERLAY_STYLES = {
 position: 'fixed',
 top: 0,
 left: 0,
 right: 0,
 bottom: 0,
 backgroundColor: 'rgba(0, 0, 0, .7)',
 zIndex: 1000
}

function InviteLink({ onClose }) {
 const groupId = useSelector(selectGroupId);
 const [url, setUrl] = useState(groupId && (window.location.origin + '/join/' + groupId))

 return ReactDom.createPortal(
  <>
   <div style={OVERLAY_STYLES} onClick={() => onClose()} />
   <div style={MODAL_STYLES}>
    <div className="addChennal">

     <form>
      <h3>Invite Link</h3>
      <p>Send this link to your friends to add them into this group.</p>

      <h5>Link</h5>
      <input value={url} type="text" readOnly />
     </form>
    </div>
   </div>
  </>,
  document.getElementById('portal')
 )
}

export default InviteLink
