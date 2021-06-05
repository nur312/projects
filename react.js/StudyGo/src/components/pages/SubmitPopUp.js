import React, { useState } from 'react';
import '../sidebar/AddChannelPopUp.css';
import ReactDom from 'react-dom';
import PublishIcon from '@material-ui/icons/Publish';
import PublishOutlinedIcon from '@material-ui/icons/PublishOutlined';
import { auth, db, firebaseApp } from '../../firebase';
import { useSelector } from 'react-redux';
import { selectChannelId } from '../../features/appSlice';
import { useAuthState } from 'react-firebase-hooks/auth';
import firebase from 'firebase';

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

function SubmitPopUp({ postId, onClose, channekIdTask }) {
  const channelId = useSelector(selectChannelId);
  const [user, loading] = useAuthState(auth);

  const [file, setFileName] = useState(null);

  const onChoosedFile = (e) => {
    const file = e.target.files[0];
    setFileName(file);
  };
  const onFileUploaded = (e) => {
    e.preventDefault();
    onClose();
    const storageRef = firebaseApp.storage().ref();
    const fileRef = storageRef.child(new Date().getTime() + file.name);
    fileRef.put(file).then((snapshot) => {
      snapshot.ref.getDownloadURL().then(function (downloadURL) {
        postId &&
          db
            .collection('channels')
            .doc(channelId ? channelId : channekIdTask)
            .collection('posts')
            .doc(postId)
            .collection('submitedWorks')
            .add({
              submitter: user.email,
              urlWork: downloadURL,
              time: firebase.firestore.FieldValue.serverTimestamp(),
              isSent: 0,
            });

        onClose();
      });
    });
  };

  return ReactDom.createPortal(
    <>
      <div style={OVERLAY_STYLES} onClick={() => onClose()} />
      <div style={MODAL_STYLES}>
        <div className='addChennal'>
          <form>
            <h3>Submit task</h3>
            <p>Send your solution to be checked from the teacher!</p>
            <div className='example-1'>
              <div className='form-group'>
                <label className='label'>
                  <i className='material-icons'>
                    <PublishOutlinedIcon />
                  </i>
                  <span className='title'>Upload file</span>
                  <input type='file' onChange={onChoosedFile} />
                </label>
              </div>
            </div>
            <div className='fileName'>
              {file && (
                <input
                  value={file.name}
                  readOnly
                  type='text'
                  style={{
                    border: 'none',
                    borderBottom: '1px solid lightGray',
                    fontSize: '16px',
                    width: '270px',
                  }}
                />
              )}
            </div>

            <button onClick={onFileUploaded}>Send</button>
          </form>
        </div>
      </div>
    </>,
    document.getElementById('portal')
  );
}

export default SubmitPopUp;
