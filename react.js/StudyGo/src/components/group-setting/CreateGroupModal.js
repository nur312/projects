import React, { useState } from 'react';
import { auth, db } from '../../firebase';
import { useDispatch } from 'react-redux';
import '../sidebar/AddChannelPopUp.css';
import ReactDom from 'react-dom';
import { useAuthState } from 'react-firebase-hooks/auth';
import { enterGroup } from '../../features/appSlice';

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

function CreateGroupModal({ onClose }) {
  const [user, loading, error] = useAuthState(auth);
  const [name, setName] = useState('');
  const [url, setUrl] = useState('');
  const dispatch = useDispatch();

  if (loading) {
    return <h3>user loading</h3>;
  }

  if (error) {
    return <h3>error auth</h3>;
  }

  const createGroup = (e) => {
    e.preventDefault();
    if (!name) alert("Without name can't create new group");
    else {
      onClose();
      db.collection('groups')
        .add({
          name: name,
          urlPic: url,
        })
        .then(function (docRef) {
          console.log('Document written with ID: ', docRef.id);

          db.collection('users')
            .doc(user.email)
            .collection('groups-id')
            .add({ id: docRef.id });

          dispatch(enterGroup({ groupId: docRef.id }));
          //обновиить currentGroup

          db.collection('users')
            .doc(user.email)
            .update({ currentGroup: docRef.id });
        })
        .catch(function (error) {
          console.error('Error adding document: ', error);
        });

    }
  };

  return ReactDom.createPortal(
    <>
      <div style={OVERLAY_STYLES} onClick={() => onClose()} />
      <div style={MODAL_STYLES}>
        <div className='addChennal'>
          <form>
            <h3>Create a group</h3>
            <p>
              From here you can create a new group, where you can communicate with friends, teachers and сolleagues.
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
            <button onClick={createGroup}>Create</button>
          </form>
        </div>
      </div>
    </>,
    document.getElementById('portal')
  );
}

export default CreateGroupModal;
