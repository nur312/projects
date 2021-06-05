import React, { useState } from 'react';
import { Avatar, TextField } from '@material-ui/core';
import ReactDom from 'react-dom';
import styled from 'styled-components';
import DescriptionOutlinedIcon from '@material-ui/icons/DescriptionOutlined';
import SaveOutlinedIcon from '@material-ui/icons/SaveOutlined';
import './WorkItem.css';
import { useParams } from 'react-router';
import { useSelector } from 'react-redux';
import { selectChannelId } from '../../features/appSlice';
import SendOutlinedIcon from '@material-ui/icons/SendOutlined';
import { db } from '../../firebase';
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

function WorkItemModal({ id, onClose, submitter, date, submit }) {
  const { postId } = useParams();
  const channelId = useSelector(selectChannelId);

  const [comment, setComment] = useState(submit.comment ? submit.comment : '');
  const [mark, setMark] = useState(submit.mark ? submit.mark : '');

  const saveReview = () => {
    if (!mark) {
      return;
    }
    onClose();
    db.collection('channels')
      .doc(channelId)
      .collection('posts')
      .doc(postId)
      .collection('submitedWorks')
      .doc(id)
      .update({
        mark: mark,
        comment: comment,
        isSent: 1,
        replyTime: firebase.firestore.FieldValue.serverTimestamp(),
      });
    db.collection('users')
      .doc(submit?.submitter)
      .collection('feedbacks')
      .add({
        channelId: channelId,
        postId: postId,
        submitId: id,
        time: firebase.firestore.FieldValue.serverTimestamp(),
      });
  };

  return ReactDom.createPortal(
    <>
      <div style={OVERLAY_STYLES} onClick={() => onClose()} />
      <div style={MODAL_STYLES}>
        <div className='work-item-modal'>
          <div className='work-item-container'>
            <div className='work-item-left'>
              <Avatar src={submitter.data().urlPic}>
                {submitter.data().name[0]}
              </Avatar>
              <div className='work-item-info'>
                <h4>{submitter?.data()?.name}</h4>
                <span>{date}</span>
              </div>
            </div>
            <div className='work-item-controllers'>
              <SidebarOption
                onClick={() => window.open(submit?.urlWork, '_blank')}
                Icon={DescriptionOutlinedIcon}
                title='Open file'
              />
            </div>
          </div>
          <div className='work-item-comment-and-mark'>
            <div className='work-item-mark'>
              <h5>Mark</h5>
              <input
                type='number'
                value={mark}
                onChange={(e) => setMark(e.target.value)}
              />
            </div>
            <div className='work-item-comment'>
              <h5>Comments</h5>
              <TextField
                value={comment}
                onChange={(e) => setComment(e.target.value)}
                id='outlined-multiline-static'
                multiline
                rows={4}
                placeholder='Type comments...'
                variant='outlined'
              />
            </div>
            <div className='work-item-controlls'>
              <SidebarOption
                onClick={() => saveReview()}
                Icon={SendOutlinedIcon}
                title='Send'
              />
            </div>
          </div>
        </div>
      </div>
    </>,
    document.getElementById('portal')
  );
}

export default WorkItemModal;

function SidebarOption({ Icon, title, onClick }) {
  return (
    <SidebarOptionContainer onClick={onClick}>
      <Icon />
      <h3>{title}</h3>
    </SidebarOptionContainer>
  );
}

const SidebarOptionContainer = styled.div`
  display: flex;
  font-size: 12px;
  align-items: center;
  margin-top: 5px;
  padding: 5px;
  cursor: pointer;
  border-radius: 16px;
  color: gray;

  :hover {
    opacity: 0.9;
    background-color: lightgray;
  }

  > h3 {
    font-weight: 700;
  }

  > h3 > span {
    padding: 15px;
  }

  > .MuiSvgIcon-root {
    margin-right: 0.5rem;
  }
`;
