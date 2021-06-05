import React, { useState } from 'react';
import './Bottom.css';
import AddIcon from '@material-ui/icons/Add';
import SidebarOption from './SidebarOption';
import SidebarSubject from './SidebarSubject';
import AddChannelPopUp from './AddChannelPopUp';
import { useSelector } from 'react-redux';
import { selectGroupId } from '../../features/appSlice';
import { useCollection } from 'react-firebase-hooks/firestore';
import { db } from '../../firebase';
import { Link } from 'react-router-dom';

function Bottom({ onClose }) {
  const groupId = useSelector(selectGroupId);

  const [channels] = useCollection(
    groupId && db.collection('groups').doc(groupId).collection('channels-id')
  );
  const [popUpAddChannel, setPopUpAddChannel] = useState(false);
  if (!groupId || !channels) {
    return null;
  }
  return (
    <div className='bottom'>
      <div
        className='bottom_addChannel'
        onClick={() => setPopUpAddChannel(true)}
      >
        <SidebarOption Icon={AddIcon} title='Add Channel' />
      </div>

      {popUpAddChannel && (
        <AddChannelPopUp onClose={() => setPopUpAddChannel(false)} />
      )}
      <div>
        {channels?.docs.map((doc) => (
          <Link to='/channel' className='middle_link' key={doc.id}>
            <SidebarSubject
              key={doc.id}
              id={doc.data().id}
              onClose={() => onClose()}
            />
          </Link>
        ))}
      </div>
    </div>
  );
}

export default Bottom;
