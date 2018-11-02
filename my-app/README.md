This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).
It implements a simple client for our twitter application.
It features two tabs (one for tweets and another for topics management) as well as a header.

## Notable dependencies
1. [react-tabs](https://github.com/reactjs/react-tabs) - Simple tabs component
2. [material-ui](https://material-ui.com/) - Used to make some components look better
3. [axios](https://www.npmjs.com/package/axios) - Promise based HTTP client for the browser and node.js
4. [react-alert](https://www.npmjs.com/package/react-alert) - Used to notify users of suspected errors
5. [react-tweet](https://www.npmjs.com/package/react-tweet) - Display a tweet in a prettier way
6. [react-websocket](https://www.npmjs.com/package/react-websocket) - Consume tweets via websocket

## Limitations
1. Currently has some hardcoded values:
    1. location of server side applications must be localhost
    2. port of topics management must be 8080
    3. port of tweeter websocket must be 8090
2. No authentication means that users can easily "pretend" to be other users
3. With the current implementation, a user will see all the tweets that interest at least one of the users (since we use one twitter stream and broadcast the messages we get from it to all of our websocket sessions)

## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.<br>
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.<br>
You will also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.<br>
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.<br>
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.<br>
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can’t go back!**

If you aren’t satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (Webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you’re on your own.

You don’t have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn’t feel obligated to use this feature. However we understand that this tool wouldn’t be useful if you couldn’t customize it when you are ready for it.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).
