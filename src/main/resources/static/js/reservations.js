const Offers = {
    stack: null,

    get() {
        const http = new XMLHttpRequest();

        http.onreadystatechange = (e) => {
            if (http.readyState === 4 && http.status === 200) {
                try {
                    // Tymczasowe dane
                	const str = http.responseText;

                    // const json = JSON.parse(http.responseText);
                    this.stack = JSON.parse(str);

                    // Usuwamy znaczek ładowania
                    document.getElementById('offers_loading').style.display = 'none';

                    // Wypełniamy tabelkę danymi
                    this.fillTable();
                } catch (e) {
                    console.log(e);

                    alert('Nie udało się poprawnie pobrać danych z bazy!');
                }
            }
        }

        //http.open('GET', 'https://api.github.com/orgs/nodejs');
        http.open('GET', '/getavailableoffers');
        http.send();
    },

    fillTable() {
        // Czyszczenie tabelki przed dodaniem
        document.getElementById('offers-content').innerHTML = '';

        let i = 1;

        // Dodawanie po kolei każdego rzędu do tabelki
        for (let offer of this.stack) {
        	let it = i;
            const row = document.createElement('div');
            offer.dom = row;
            row.classList.add('table-offer-row');
            row.classList.add('table-contentRow');

            let child = document.createElement('div');
            child.innerHTML = it;
            row.appendChild(child);

            child = document.createElement('div');
            child.innerHTML = offer.manufacturer;
            row.appendChild(child);

            child = document.createElement('div');
            child.innerHTML = offer.model;
            row.appendChild(child);

            child = document.createElement('div');
            child.innerHTML = offer.type;
            row.appendChild(child);

            child = document.createElement('div');
            child.innerHTML = offer.price;
            row.appendChild(child);

            child = document.createElement('div');
            child.innerHTML = offer.fuelConsumption;
            row.appendChild(child);

            child = document.createElement('div');

            let img = new Image();
            img.onclick = () => {
                Offers.accept(offer, it);
            }
            img.src = 'icons/tick.png';
            img.title = 'Dodaj rezerwację';
            img.alt = 'Dodaj rezerwację';
            child.appendChild(img);

            row.appendChild(child);

            i++;

            document.getElementById('offers-content').appendChild(row);
        }
    },

    accept(offer, no) {
        if (confirm(`Czy na pewno zatwierdzić ofertę ${no}?`)) {
            //USUWANIE Z BAZY
            const http = new XMLHttpRequest();

            http.onreadystatechange = (e) => {
                if (http.readyState === 4 && http.status === 200) {
                    try {
                        // Pobieramy index rezerwacji do usunięcia
                        const it = Offers.stack.findIndex(el => {
                            return el === offer;
                        });

                        // Jeżeli została znaleziona
                        if (it > -1) {
                            // Usuwamy ją z tablicy
                            Offers.stack.splice(it, 1);

                            // Usuwamy jej element z tabliy
                            offer.dom.remove();

                            Reservations.get();
                        }
                    } catch (e) {
                        alert('Nie udało się poprawnie podjąć oferty!');
                    }
                }
            }

            http.open('GET', `/createreservation?offerID=${offer.offerID}`);
            http.send();
        }
    }
}

const Reservations = {
    stack: null,

    get() {
        const http = new XMLHttpRequest();

        http.onreadystatechange = (e) => {
            if (http.readyState === 4 && http.status === 200) {
                try {
                    // Tymczasowe dane
                    const str = http.responseText;

                    // const json = JSON.parse(http.responseText);
                    this.stack = JSON.parse(str);

                    // Usuwamy znaczek ładowania
                    document.getElementById('loading').style.display = 'none';

                    // Wypełniamy tabelkę danymi
                    console.log(0);
                    this.fillTable();
                } catch (e) {
                    alert('Nie udało się poprawnie pobrać danych z bazy!');
                }
            }
        }

        http.open('GET', '/getreservations');
        http.send();
    },

    fillTable() {
        // Czyszczenie tabelki przed dodaniem
        document.getElementById('reservations-content').innerHTML = '';
  
        let i=1;

        // Dodawanie po kolei każdego rzędu do tabelki

        for (let reservation of this.stack) {
        	let it = i;
            console.log(reservation);
            
            const row = document.createElement('div');
            reservation.dom = row;
            row.classList.add('table-row');
            row.classList.add('table-contentRow');

            let child = document.createElement('div');
            child.innerHTML = it;
            row.appendChild(child);

            child = document.createElement('div');
            child.innerHTML = reservation.manufacturer;
            row.appendChild(child);

            child = document.createElement('div');
            child.innerHTML = reservation.model;
            row.appendChild(child);

            child = document.createElement('div');
            child.innerHTML = reservation.price;
            row.appendChild(child);

            let tmpDate =  new Date(reservation.startDate);
            child = document.createElement('div');
            child.innerHTML = `${tmpDate.getFullYear()}-${tmpDate.getMonth()+1}-${tmpDate.getDate()}`;
            row.appendChild(child);

            tmpDate = new Date(reservation.endDate);
            child = document.createElement('div');
            child.innerHTML = `${tmpDate.getFullYear()}-${tmpDate.getMonth()+1}-${tmpDate.getDate()}`;
            row.appendChild(child);

            child = document.createElement('div');

            let img = new Image();
            img.onclick = () => {
                Reservations.remove(reservation, it);
            }
            img.src = 'icons/cancel.png';
            img.title = 'Usuń rezerwację';
            img.alt = 'Usuń rezerwację';
            child.appendChild(img);

            row.appendChild(child);
            i++;

            document.getElementById('reservations-content').appendChild(row);
        }
    },

    remove(reservation, no) {
        if (confirm(`Czy na pewno usunąć rezerwację ${no}?`)) {
            //USUWANIE Z BAZY
            const http = new XMLHttpRequest();

            http.onreadystatechange = (e) => {
                if (http.readyState === 4 && http.status === 200) {
                    try {
                        // Pobieramy index rezerwacji do usunięcia
                        const it = Reservations.stack.findIndex(el => {
                            return el === reservation;
                        });

                        // Jeżeli została znaleziona
                        if (it > -1) {
                            // Usuwamy ją z tablicy
                            Reservations.stack.splice(it, 1);
                            Offers.get();

                            // Usuwamy jej element z tabliy
                            reservation.dom.remove();
                        }
                    } catch (e) {
                        alert('Nie udało się poprawnie usunąć rezerwacji z bazy!');
                    }
                }
            }

            http.open('DELETE', '/deletereservation');
            http.send(reservation.rentalID);
        }
    }
};

window.onload = () => {
    Reservations.get();
    Offers.get();
}
