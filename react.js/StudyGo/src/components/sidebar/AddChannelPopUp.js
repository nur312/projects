import React, { useState } from 'react'
import './AddChannelPopUp.css'
import ReactDom from 'react-dom'
import { useDispatch, useSelector } from 'react-redux'
import { enterChannel, enterGroup, selectGroupId } from '../../features/appSlice'
import { useAuthState } from 'react-firebase-hooks/auth'
import { auth, db } from '../../firebase'

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

function AddChannelPopUp({ onClose }) {
  const groupId = useSelector(selectGroupId)
  const [user, loading, error] = useAuthState(auth);
  const dispatch = useDispatch();
  const [name, setName] = useState('')
  const [description, setDescription] = useState('')
  const [url, setUrl] = useState('')


  const createChannel = (e) => {
    e.preventDefault()
    if (!name) {
      alert('Without name can\'t create new channel');
      return;
    }
    onClose();
    db.collection('channels')
      .add({
        name: name,
        description: description,
        urlPic: url,
        creator: user.email,
      })
      .then(function (docRef) {
        console.log('Document written with ID: ', docRef.id);

        db.collection('groups')
          .doc(groupId)
          .collection('channels-id')
          .add({ id: docRef.id });

        dispatch(enterChannel({ channelId: docRef.id }));
        //обновиить currentGroup

        db.collection('users')
          .doc(user.email)
          .update({ currentChannel: docRef.id });
      })
      .catch(function (error) {
        console.error('Error adding document: ', error);
      });
  }


  return ReactDom.createPortal(
    <>
      <div style={OVERLAY_STYLES} onClick={() => onClose()} />
      <div style={MODAL_STYLES}>
        <div className="addChennal">

          <form>
            <h3>Create a Channel</h3>
            <p>Channels are where your team communicates. They’re best when organized around a topic — #marketing, for example.</p>
            <h5>Name</h5>
            <input value={name} onChange={(e) => { setName(e.target.value); }} type="text" />
            <h5>Description</h5>
            <input value={description} onChange={(e) => { setDescription(e.target.value); }} placeholder='optional' type="text" />
            <h5>Url Picture</h5>
            <input value={url} onChange={(e) => { setUrl(e.target.value); }} placeholder='optional' type="text" />
            <button onClick={createChannel}>Create</button>
          </form>
        </div>
      </div>
    </>,
    document.getElementById('portal')
  )
}

export default AddChannelPopUp
