import React from 'react';

const Persons = ({ persons }) => {
    return (
        <div class="col">
            <h1>Personen Liste</h1>
            {persons.map((person) => (
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">{person.name}</h5>
                        <h6 class="card-subtitle mb-2 text-muted">{person.email}</h6>
                        <p class="card-text">{person.motto}</p>
                    </div>
                </div>
            ))}
        </div>
    )
};

export default Persons
