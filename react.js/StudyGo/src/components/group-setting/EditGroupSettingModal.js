import React, { useState } from 'react';
import '../sidebar/AddChannelPopUp.css';
import ReactDom from 'react-dom';
import { db } from '../../firebase';
import { useSelector } from 'react-redux';
import { selectGroupId } from '../../features/appSlice';
import { useDocument } from 'react-firebase-hooks/firestore';

const MODAL_STYLES = {
  position: 'fixed',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  zIndex: 1000,
};

const OVERLAY_STYLES = {
  position: 'fixed',
  top: 0,
  left: 0,
  right: 0,
  bottom: 0,
  backgroundColor: 'rgba(0, 0, 0, .7)',
  zIndex: 1000,
};

function EditGroupSettingModal({ onClose }) {
  const groupId = useSelector(selectGroupId);
  const [group] = useDocument(groupId && db.collection('groups').doc(groupId));
  const [name, setName] = useState('');
  const [url, setUrl] = useState('');
  if (!group) {
    return null;
  }

  if (!name && group?.data()?.name) setName(group?.data()?.name);

  if (!url && group?.data()?.urlPic) setUrl(group?.data()?.urlPic);

  const editGroup = (e) => {
    e.preventDefault();
    if (!name) {
      alert("Without name can't edit group setting");
      return;
    }
    onClose();
    db.collection('groups').doc(groupId).update({ name: name, urlPic: url });
  };

  return ReactDom.createPortal(
    <>
      <div style={OVERLAY_STYLES} onClick={() => onClose()} />
      <div style={MODAL_STYLES}>
        <div className='addChennal'>
          <form>
            <h3>Group setting</h3>
            <p>
              From here you can manage the setting of current group.
            </p>
            <h5>Name</h5>
            <input
              value={name}
              onChange={(e) => {
                setName(e.target.value);
              }}
              type='text'
            />
            <h5>Url Picture</h5>
            <input
              value={url}
              onChange={(e) => {
                setUrl(e.target.value);
              }}
              placeholder='optional'
              type='text'
            />
            <button onClick={editGroup}>Save</button>
          </form>
        </div>
      </div>
    </>,
    document.getElementById('portal')
  );
}

export default EditGroupSettingModal;
