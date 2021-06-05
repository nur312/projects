import React, { useState } from 'react';
import './ChannelBuildPost.css';
import TextField from '@material-ui/core/TextField';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';
import InputOption from './InputOption';
import Button from '@material-ui/core/Button';
import AttachFileOutlinedIcon from '@material-ui/icons/AttachFileOutlined';
import FileAttachModal from './FileAttachModal';
import { useSelector } from 'react-redux';
import { selectChannelId, selectGroupId } from '../../features/appSlice';
import { auth, db } from '../../firebase';
import firebase from 'firebase';
import { useAuthState } from 'react-firebase-hooks/auth';

const styles = {
  container: {
    display: 'flex',
    flexWrap: 'wrap',
  },
  textField: {
    fontSize: '10px',
  },
  //style for font size
  resize: {
    fontSize: '10px',
  },
};

function ChannelBuildPost() {
  const [user, loading] = useAuthState(auth);
  const [input, setInput] = useState('');
  const [deadline, setDeadline] = useState('2021-05-24T10:30');
  const [showFileUploadModal, setShowFileUploadModal] = useState(false);
  const [urlFile, setUrlFile] = useState('');
  const [selectedValue, setSelectedValue] = React.useState('post');
  const channelId = useSelector(selectChannelId)
  const groupId = useSelector(selectGroupId)
  const sendPost = (e) => {
    e.preventDefault();
    const time = firebase.firestore.FieldValue.serverTimestamp()
    if (input) {
      db.collection('channels').doc(channelId).collection('posts').add({ email: user.email, time: time, input: input, postType: selectedValue, deadline: deadline, urlFile: urlFile }).then(function (docRef) {
        if (selectedValue === 'task') {
          db.collection('groups').doc(groupId).collection('tasks').add({ channelId: channelId, postId: docRef.id, time: time })
        }
        else if (selectedValue === 'deadline') {
          db.collection('groups').doc(groupId).collection('deadlines').add({ channelId: channelId, postId: docRef.id, time: time })
        }
        setInput('');
        setUrlFile('');
      })
    }
  };

  const handleChange = (event) => {
    setSelectedValue(event.target.value);
  };
  return (
    <div className='channelBuildPost'>
      <div className='channelBuildPost_inputContainer'>
        <div className='channelBuildPost_input'>
          {/* <CreateIcon /> */}
          <form>
            <TextField
              value={input}
              onChange={(e) => setInput(e.target.value)}
              id='outlined-multiline-static'
              multiline
              rows={4}
              placeholder='Create post...'
              variant='outlined'
            />

            <div className='channelBuildPost_options'>
              <FormControl component='fieldset'>
                <RadioGroup
                  row
                  aria-label='position'
                  name='position'
                  defaultValue='top'
                >
                  <FormControlLabel
                    value='top'
                    control={
                      <Radio
                        color='primary'
                        checked={selectedValue === 'post'}
                        onChange={handleChange}
                        value='post'
                        name='radio-button-demo'
                        inputProps={{ 'aria-label': 'A' }}
                      />
                    }
                    label='Post'
                  />
                  <FormControlLabel
                    value='start'
                    control={
                      <Radio
                        color='primary'
                        checked={selectedValue === 'task'}
                        onChange={handleChange}
                        value='task'
                        name='radio-button-demo'
                        inputProps={{ 'aria-label': 'B' }}
                      />
                    }
                    label='Task'
                  />
                  <FormControlLabel
                    value='bottom'
                    control={
                      <Radio
                        color='primary'
                        checked={selectedValue === 'deadline'}
                        onChange={handleChange}
                        value='deadline'
                        name='radio-button-demo'
                        inputProps={{ 'aria-label': 'C' }}
                      />
                    }
                    label='Deadline'
                  />
                </RadioGroup>
              </FormControl>
            </div>

            <div className='channelBuildPost_controllers'>
              <div
                className='fileChooser'
                onClick={() => setShowFileUploadModal(true)}
              >
                <InputOption title='File' Icon={AttachFileOutlinedIcon} />
              </div>

              {showFileUploadModal && (
                <FileAttachModal
                  onClose={() => setShowFileUploadModal(false)} setUrlFile={setUrlFile}
                />
              )}

              {selectedValue === 'deadline' && (
                <TextField
                  value={deadline}
                  onChange={(e) => setDeadline(e.target.value)}
                  id='datetime-local'
                  type='datetime-local'
                  InputLabelProps={{
                    shrink: true,
                  }}
                />
              )}
            </div>
            <div className='formBtn'>
              <Button onClick={sendPost} variant='contained' color='primary'>
                Post
              </Button>
            </div>
          </form>
        </div>

      </div>
    </div>
  );
}

export default ChannelBuildPost;
