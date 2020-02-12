// Kolejka
let loginQueue = false;

function checkLogin() {
    // Sprawdzanie kolejki
    if (!loginQueue) {
        // Kolejka...
        loginQueue = true;

        // Zmiana stanu przycisku i pól
        document.getElementById('loginBtn').innerHTML = 'Ładowanie...';
        document.getElementById('loginBtn').style.background = '#0C609C';
        document.getElementById('login').style.borderColor = '#ccc';
        document.getElementById('password').style.borderColor = '#ccc';

        // Pobranie danych
        const login = document.getElementById('login').value.trim();
        const password = document.getElementById('password').value;

        // Sprawdzenie danych
        if (login.length <= 0) {
            document.getElementById('login').style.borderColor = '#ff0000';
            document.getElementById('loginBtn').innerHTML = 'Nazwa użytkownika nie może być pusta';
            document.getElementById('loginBtn').style.background = '#ff0000';
            loginQueue = false;
            return;
        }

        if (password.length <= 0) {
            document.getElementById('password').style.borderColor = '#ff0000';
            document.getElementById('loginBtn').innerHTML = 'Hasło nie może być puste';
            document.getElementById('loginBtn').style.background = '#ff0000';
            loginQueue = false;
            return;
        }

        // Tutaj połączenie z backend żeby sprawdzić czy wszystko ok
        const http = new XMLHttpRequest();

        http.onreadystatechange = (e) => {
            if (http.readyState === 4 && http.status === 200) {
                try {
                    if (http.responseText !== '-1') {
                        document.getElementById('loginBtn').innerHTML = 'Zalogowany';
                        document.getElementById('loginBtn').style.background = '#25c425';
                        
                        if(http.responseText === '0') {
                        	window.location.href = '/reservations';
                        }
                        else {
                        	window.location.href = 'http://localhost:3000/';
                        }
                    } else {
                        document.getElementById('loginBtn').innerHTML = 'Zła nazwa użytkownika lub hasło';
                        document.getElementById('loginBtn').style.background = '#ff0000';
                    }
                } catch (e) {
                    document.getElementById('loginBtn').innerHTML = 'Błędna odpowiedź serwera...';
                    document.getElementById('loginBtn').style.background = '#ff0000';

                    loginQueue = false;
                }

            } else {
                document.getElementById('loginBtn').innerHTML = 'Błąd serwera...';
                document.getElementById('loginBtn').style.background = '#ff0000';
                loginQueue = false;
            }
        }

        http.open('GET', `/loginsite?login=${login}&password=${password}`);
        http.send();
    }
}

// Przypisanie akcji do przycisku logowania
document.getElementById('loginBtn').addEventListener('click', () => {
    // Sprawdzenie logowania
    checkLogin();
});
