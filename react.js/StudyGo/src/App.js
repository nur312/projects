import React, { useState } from 'react';
import './styles/Sidebar.css';
import './App.css';

import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom';
import { useHistory } from 'react-router-dom';
import { useAuthState } from 'react-firebase-hooks/auth';
import { auth, db } from './firebase';
import { Avatar, CssBaseline } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import CloseIcon from '@material-ui/icons/Close';
import MenuIcon from '@material-ui/icons/Menu';
import Login from './components/Login';
import Schedule from './components/pages/Schedule';
import Tasks from './components/pages/Tasks';
import Deadlines from './components/pages/Deadlines';
import HomePage from './components/pages/HomePage';
import ChannelPage from './components/pages/ChannelPage';
import styled from 'styled-components';
import Sidebar from './components/Sidebar';
import StudyGoLogo from './components/sidebar/StudyGoLogo';
import UserSettingSidebar from './components/user-setting/UserSettingSidebar';
import GroupSettingMenu from './components/group-setting/GroupSettingMenu';
import CheckPage from './components/check-works/CheckPage';
import AssignmentReturnedOutlinedIcon from '@material-ui/icons/AssignmentReturnedOutlined';
import { useDispatch, useSelector } from 'react-redux';
import {
  enterChannel,
  enterGroup,
  selectChannelId,
  selectGroupId,
} from './features/appSlice';
import { useDocument } from 'react-firebase-hooks/firestore';
import JoinPage from './components/pages/JoinPage';
import Feedbacks from './components/feedbacks/Feedbacks';
import MenuBookIcon from '@material-ui/icons/MenuBook';

function App() {
  const dispatch = useDispatch();
  let history = useHistory();
  const [user, loading, error] = useAuthState(auth);

  const usersRef = db.collection('users').doc(user?.email);

  usersRef.get().then((docSnapshot) => {
    if (!docSnapshot.exists && user) {
      usersRef.set({ name: user.displayName }); // create the document
    }
  });

  const groupId = useSelector(selectGroupId);
  const channelId = useSelector(selectChannelId);

  if (!groupId && user) {
    const docRef = db.collection('users').doc(user.email);

    docRef
      .get()
      .then((doc) => {
        if (doc.exists) {
          const id = doc.data().currentGroup;
          if (id) {
            dispatch(enterGroup({ groupId: id }));
          }
        } else {
          // doc.data() will be undefined in this case
          console.log('No such document!');
        }
      })
      .catch((error) => {
        console.log('Error getting document:', error);
      });
  }

  if (!channelId && !groupId && user) {
    const docRef = db.collection('users').doc(user.email);

    docRef
      .get()
      .then((doc) => {
        if (doc.exists) {
          const id = doc.data().currentChannel;
          if (id) {
            dispatch(enterChannel({ channelId: id }));
          }
        } else {
          // doc.data() will be undefined in this case
          console.log('No such document!');
        }
      })
      .catch((error) => {
        console.log('Error getting document:', error);
      });
  }

  const [groupDetails] = useDocument(
    groupId && db.collection('groups').doc(groupId)
  );

  const [sidebar, setSidebar] = useState(false);
  const [sidebarUserSetting, setSidebarUserSetting] = useState(false);
  const [groupSetting, setGroupSetting] = useState(false);

  const showSidebar = () => {
    setSidebar(!sidebar);
    setSidebarUserSetting(false);
    setGroupSetting(false);
  };
  const showUserSetting = () => {
    setSidebarUserSetting(!sidebarUserSetting);
    setGroupSetting(false);
    setSidebar(false);
  };
  const showGroupSetting = () => {
    setGroupSetting(!groupSetting);
    setSidebarUserSetting(false);
    setSidebar(false);
  };
  if (loading) {
    return <></>;
  }

  if (error) {
    return (
      <>
        <h3>Auth error...</h3>
      </>
    );
  }

  const showFeedbacks = () => {
    console.log('his');
    history?.push('/feedbacks');
  };

  return (
    <div className='app' style={{ overflowY: 'auto' }}>
      <CssBaseline />
      <Router>
        {!user ? (
          <Login />
        ) : (
          <>
            <AppBarContainer>
              {sidebar ? (
                <div className='sidebar-control'>
                  <CloseIcon onClick={showSidebar} />
                </div>
              ) : (
                <div className='sidebar-control'>
                  <MenuIcon onClick={showSidebar} />
                </div>
              )}
              <HeaderAvatar
                style={{ marginLeft: '1rem', marginRight: 'auto' }}
                onClick={() => showGroupSetting()}
                src={groupDetails?.data()?.urlPic}
                alt='БПИ197'
              />

              <StudyGoLogo
                style={{ marginLeft: 'auto', marginRight: 'auto' }}
              />
              <Link
                to='/feedbacks'
                style={{ marginLeft: 'auto', marginRight: '1rem' }}
              >
                <MenuBookIcon
                  style={{ textDecoration: 'none', color: 'white' }}
                  onClick={() => setSidebar(false)}
                />
              </Link>

              <HeaderAvatar
                onClick={() => showUserSetting()}
                src={user?.photoURL}
                alt={user?.displayName}
              />
              {/* <Button color='inherit' onClick={() => auth.signOut()}>
                Sign out
              </Button> */}
            </AppBarContainer>

            <Sidebar sidebar={sidebar} onClose={() => setSidebar(false)} />

            <UserSettingSidebar
              userSettingSidebar={sidebarUserSetting}
              onClose={() => setSidebarUserSetting(false)}
            />
            <GroupSettingMenu
              groupSetting={groupSetting}
              onClose={() => setGroupSetting(false)}
            />
            <div
              className={
                sidebar ? 'content-container-moved' : 'content-container'
              }
            >
              <Switch>
                <Route path='/' exact component={HomePage} />
                <Route path='/schedule' component={Schedule} />
                <Route path='/tasks' component={Tasks} />
                <Route path='/deadlines' component={Deadlines} />
                <Route path='/channel' component={ChannelPage} />
                <Route path='/check/:postId' component={CheckPage} />
                <Route path='/join/:groupId' component={JoinPage} />
                <Route path='/feedbacks' component={Feedbacks} />
              </Switch>
            </div>
          </>
        )}
      </Router>
    </div>
  );
}

export default App;

const AppBarContainer = styled.div`
  display: flex;
  width: 100%;
  height: var(--app-bar-height);

  position: fixed;
  align-items: center;
  justify-content: space-between;
  background-color: var(--slack-color);
  color: white;
  padding-left: 0.5rem;
  border-bottom: 1px solid lightgray;
`;

const HeaderAvatar = styled(Avatar)`
  cursor: pointer;
  :hover {
    opacity: 0.8;
  }

  margin-right: 1rem;
`;
