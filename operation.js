//Creazione Utente
db.createUser({
    user: "WebClient",
    pwd: "123_Stella",
    roles: [{ role: "readWrite", db: "gestfid" }]
})

//Inserimento Clienti Fidelity
db.clienti.insertOne({
    codfid: '67100947',
    nominativo: 'Nicola La Rocca',
    indirizzo: 'Via dei Mille, 52',
    comune: 'Sassari',
    cap: '07100',
    prov: 'SS',
    telefono: '0388123456',
    mail: 'nicola_larocca@xantrix.it',
    attivo: true,
    spese: { numero: 10, valore: 150.69 },
    datacreazione: new Date(),
    cards: { bollini: 180, ultimaspesa: '2019-12-15' }
});

//Visualizzare tutti i documenti
db.clienti.find();

//Inserimento multiplo
db.clienti.insertMany([
    { codfid: '67100920', nominativo: 'NADIA BIANCHI', attivo: true },
    {
        codfid: '67100900',
        nominativo: 'ARTURO VIRDIS',
        mail: '',
        attivo: true,
        cards: { bollini: 100, ultimaspesa: new Date() }
    },
    {
        codfid: '67100876',
        nominativo: 'CATERINA DEMARTIS',
        indirizzo: 'P.ZZA GIOVANNI XXIII',
        mail: 'dema@test.it',
        attivo: true,
        cards: { bollini: 100, ultimaspesa: new Date() }
    },
    {
        codfid: '000473202',
        nominativo: 'BARILLA CORNETTI INTEGRALI 240 GR.',
        attivo: true,
        cards: { bollini: 100, ultimaspesa: new Date() }
    }
]);

//Modifica documento
db.clienti.updateOne({ codfid: '67100920' }, {
        $set: {
            cards: { bollini: 360, ultimaspesa: new Date() }
        }
    }

);

//Ricerca di un documento
db.clienti.findOne({
    codfid: '67100920'
}, {
    _id: 0,
    nominativo: 1
});

db.clienti.insertMany([{
        codfid: '67100885',
        nominativo: 'MARIA ROSARIA VIRDIS',
        attivo: true,
        prov: 'SS',
        cards: { bollini: 145, ultimaspesa: new Date() }
    },
    {
        codfid: '67100900',
        nominativo: 'RITA VIRDIS',
        mail: '',
        attivo: true,
        prov: 'SS',
        cards: { bollini: -2, ultimaspesa: new Date() }
    }
]);


//Ricerca per parte stringa
db.clienti.find({
    nominativo: /VIRDIS/
}, {
    _id: 0
}).sort({ 'codfid': 1 });

db.clienti.insertMany([{
        codfid: '67000092',
        nominativo: 'MARIO MORO',
        attivo: true,
        prov: 'SS',
        cards: { bollini: 1045, ultimaspesa: new Date() }
    },
    {
        codfid: '67000095',
        nominativo: 'CATERINA CRESPI',
        mail: '',
        attivo: true,
        prov: 'SS',
        cards: { bollini: 890, ultimaspesa: new Date() }
    }
]);

//filtro documento interno
db.clienti.find({
    'cards.bollini': { $gt: 10 }
}, {
    _id: 0
}).sort({ 'cards.bollini': 1 });

//filtro documento interno
db.clienti.find({
    'cards.bollini': { $gt: 50, $lt: 500 }
}, {
    _id: 0
}).sort({ 'cards.bollini': 1 }).limit(2);

//eliminazione documento
db.clienti.deleteOne({ codfid: '67000095' })

//eliminazione diversi documenti
db.clienti.deleteMany({
    'cards.bollini': { $lt: 0 }
})

//Eliminazione totale
db.clienti.drop()


db.clienti.insertOne({
    codfid: '67100947',
    nominativo: 'Nicola La Rocca',
    indirizzo: 'Via dei Mille, 52',
    comune: 'Sassari',
    cap: '07100',
    prov: 'SS',
    telefono: '0388123456',
    mail: 'nicola_larocca@xantrix.it',
    attivo: true,
    datacreazione: new Date(),
    cards: { bollini: 180, ultimaspesa: '2019-12-15' }
});

db.clienti.createIndex({ "codfid": 1 }, { unique: true })