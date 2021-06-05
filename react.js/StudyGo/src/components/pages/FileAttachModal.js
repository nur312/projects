import React, { useState } from 'react';
import '../sidebar/AddChannelPopUp.css';
import ReactDom from 'react-dom';
import PublishOutlinedIcon from '@material-ui/icons/PublishOutlined';
import { firebaseApp } from '../../firebase'

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

function FileAttachModal({ onClose, setUrlFile }) {
  const [file, setFileName] = useState(null);

  const onChoosedFile = (e) => {
    const file = e.target.files[0];
    setFileName(file);
  }

  const onFileUploaded = (e) => {
    e.preventDefault();
    onClose();
    const storageRef = firebaseApp.storage().ref();
    const fileRef = storageRef.child(new Date().getTime() + file.name)
    fileRef.put(file).then((snapshot) => {
      snapshot.ref.getDownloadURL().then(function (downloadURL) {
        setUrlFile(downloadURL)
      });
    })
  };

  return ReactDom.createPortal(
    <>
      <div style={OVERLAY_STYLES} onClick={() => onClose()} />
      <div style={MODAL_STYLES}>
        <div className='addChennal'>
          <form>
            <h3>Attach an assignment</h3>
            <p>
              Choose a file, which you want to attach it with your post!
            </p>
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
            <div className="fileName">
              {file && <input value={file.name} readOnly type='text' style={{ border: 'none', borderBottom: '1px solid lightGray', fontSize: '16px', width: '270px' }} />}
            </div>
            <button onClick={onFileUploaded}>Attach</button>
          </form>
        </div>
      </div>
    </>,
    document.getElementById('portal')
  );
}

export default FileAttachModal;
