import firebase from 'firebase'

/*const firebaseConfig = {
  apiKey: 'AIzaSyBoQX3twU5_zKzqsOF871_Cn3LYHWsZzns',
  authDomain: 'yen-studygo.firebaseapp.com',
  projectId: 'yen-studygo',
  storageBucket: 'yen-studygo.appspot.com',
  messagingSenderId: '549095636780',
  appId: '1:549095636780:web:9a6528517a2d2697787689',
}*/

const firebaseConfig = {
  apiKey: "AIzaSyCiubAPaXT04r6w5HZs-LhD3tlQ_JFXZnw",
  authDomain: "study-go-yen.firebaseapp.com",
  projectId: "study-go-yen",
  storageBucket: "study-go-yen.appspot.com",
  messagingSenderId: "319882428429",
  appId: "1:319882428429:web:2bf934666b856a84c35544"
};

const firebaseApp = firebase.initializeApp(firebaseConfig)

const db = firebaseApp.firestore()

const auth = firebase.auth()

const provider = new firebase.auth.GoogleAuthProvider()

export { auth, db, provider, firebaseApp }
