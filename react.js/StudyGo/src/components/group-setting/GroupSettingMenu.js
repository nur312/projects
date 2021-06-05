import React, { useState } from 'react';
import './GroupSettingMenu.css';
import { useAuthState } from 'react-firebase-hooks/auth';
import { auth, db } from '../../firebase';
import styled from 'styled-components';
import { Avatar, Button } from '@material-ui/core';
import EditIcon from '@material-ui/icons/Edit';
import EditOutlinedIcon from '@material-ui/icons/EditOutlined';
import PeopleOutlineIcon from '@material-ui/icons/PeopleOutline';
import PeopleAltIcon from '@material-ui/icons/PeopleAlt';
import LinkIcon from '@material-ui/icons/Link';
import ExitToAppIcon from '@material-ui/icons/ExitToApp';
import AddOutlinedIcon from '@material-ui/icons/AddOutlined';
import CreateGroupModal from './CreateGroupModal';
import EditGroupSettingModal from './EditGroupSettingModal';
import InviteLink from './InviteLink';
import ReactDom from 'react-dom';
import { useCollection, useDocument } from 'react-firebase-hooks/firestore';
import { useDispatch, useSelector } from 'react-redux';
import {
  enterChannel,
  enterGroup,
  selectGroupId,
} from '../../features/appSlice';
import { useHistory } from 'react-router';

function SidebarOption({ Icon, title, action }) {
  return (
    <SidebarOptionContainer onClick={action}>
      <Icon />
      <h3>{title}</h3>
    </SidebarOptionContainer>
  );
}

function GroupItem({ id }) {
  const dispatch = useDispatch();
  const [group] = useDocument(id && db.collection('groups').doc(id));
  const [user, loading, error] = useAuthState(auth);
  const groupId = useSelector(selectGroupId);
  const history = useHistory();
  return (
    <SidebarOptionContainer
      onClick={() => {
        if (id !== groupId) {
          dispatch(enterGroup({ groupId: id }));
          dispatch(enterChannel({ channelId: null }));
          db.collection('users')
            .doc(user.email)
            .update({ currentGroup: id, currentChannel: null });
          history?.push('/');
        }
      }}
    >
      <Avatar
        src={group?.data()?.urlPic}
        style={{
          width: '30px',
          height: '30px',
          marginRight: '10px',
          marginLeft: '30px',
        }}
      />
      <h3>{group?.data()?.name}</h3>
    </SidebarOptionContainer>
  );
}
const OVERLAY_STYLES = {
  position: 'fixed',
  top: 0,
  left: 0,
  right: 0,
  bottom: 0,
  zIndex: 998,
};
const MODAL_STYLES = {
  position: 'fixed',
  top: '50%',
  left: '50%',
  width: '100%',
  height: '100%',
  transform: 'translate(-50%, -50%)',
};
const MODAL_STYLES_HIDE = {
  zIndex: -100,
};

function GroupSettingMenu({ groupSetting, onClose }) {
  const dispatch = useDispatch();
  const [createGroupModal, setCreateGroupModal] = useState(false);
  const [editGroupModal, setEditGroupModal] = useState(false);
  const [inviteLink, setInviteLink] = useState(false);

  const [user] = useAuthState(auth);
  const groupId = useSelector(selectGroupId);
  const [group] = useDocument(groupId && db.collection('groups').doc(groupId));

  const [groups] = useCollection(
    db.collection('users').doc(user.email).collection('groups-id')
  );

  if (!user || !group || !groups) {
    return null;
  }

  const leaveGroup = () => {
    db.collection('users')
      .doc(user.email)
      .collection('groups-id')
      .where('id', '==', groupId)
      .get()
      .then((query) => {
        query.forEach((doc) => {
          db.collection('users')
            .doc(user.email)
            .collection('groups-id')
            .doc(doc.id)
            .delete();
        });

        dispatch(enterGroup({ groupId: null }));
        dispatch(enterChannel({ channelId: null }));

        db.collection('users')
          .doc(user.email)
          .update({ currentGroup: null, currentChannel: null });
      });
  };

  return ReactDom.createPortal(
    <>
      {
        <div style={groupSetting ? MODAL_STYLES : MODAL_STYLES_HIDE}>
          <div
            style={groupSetting ? OVERLAY_STYLES : MODAL_STYLES_HIDE}
            onClick={() => onClose()}
          />
          <nav
            className={groupSetting ? 'group-menu active' : 'group-menu-hide'}
          >
            <div className='group-menu-items'>
              <HeaderAvatar
                src={group?.data()?.urlPic}
                alt={group?.data()?.name[0]}
              />
              <h4>{group?.data()?.name}</h4>
              <div className='groupOptions'>
                <SidebarOption
                  action={() => setEditGroupModal(true)}
                  title='Group setting'
                  Icon={EditOutlinedIcon}
                />
                {editGroupModal && (
                  <EditGroupSettingModal
                    onClose={() => setEditGroupModal(false)}
                  />
                )}
                <SidebarOption
                  action={() => setInviteLink(true)}
                  title='Invite link'
                  Icon={LinkIcon}
                />
                {inviteLink && (
                  <InviteLink onClose={() => setInviteLink(false)} />
                )}
                <SidebarOption
                  action={() => leaveGroup()}
                  title='Leave group'
                  Icon={ExitToAppIcon}
                />
              </div>
              <div className='groupsController'>
                <SidebarOption
                  action={() => setCreateGroupModal(true)}
                  title='Create new group'
                  Icon={AddOutlinedIcon}
                />

                {createGroupModal && (
                  <CreateGroupModal
                    onClose={() => setCreateGroupModal(false)}
                  />
                )}

                {groups?.docs.map((doc) => (
                  <GroupItem key={doc.id} id={doc.data().id} />
                ))}
              </div>
            </div>
          </nav>
        </div>
      }
    </>,
    document.getElementById('portal')
  );
}

export default GroupSettingMenu;

const HeaderAvatar = styled(Avatar)`
  cursor: pointer;
  :hover {
    opacity: 0.8;
  }
  width: 3.5rem;
  height: 3.5rem;
`;

const SidebarOptionContainer = styled.div`
  display: flex;
  font-size: 12px;
  align-items: center;
  padding: 10px 20px;
  cursor: pointer;
  width: 100%;

  :hover {
    opacity: 0.9;
    background-color: #340e36;
  }

  > h3 {
    font-weight: 500;
    color: white;
  }

  > h3 > span {
    padding: 15px;
  }

  > .MuiSvgIcon-root {
    margin-right: 0.5rem;
    color: white;
  }
`;
