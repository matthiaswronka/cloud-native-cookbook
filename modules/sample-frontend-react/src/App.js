import React, {Component} from 'react';
import Persons from "./components/persons";

class App extends Component {

    state = {
        persons: []
    }

    componentDidMount() {
        fetch(window._env_.API_URL + '/persons')
            .then(res => res.json())
            .then((data) => {
                this.setState({ persons: data })
            })
            .catch(console.log)
    }

    render() {
        return (
            <Persons persons={this.state.persons} />
        );
    }
}
export default App;
