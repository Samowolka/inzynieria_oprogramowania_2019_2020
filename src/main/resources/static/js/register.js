// Kolejka
let registerQueue = false;

function checkRegister() {
    // Sprawdzanie kolejki
    if (!registerQueue) {
        // Kolejka...
        registerQueue = true;

        // Zmiana stanu przycisku i pól
        document.getElementById('loginBtn').innerHTML = 'Ładowanie...';
        document.getElementById('loginBtn').style.background = '#0C609C';
        document.getElementById('login').style.borderColor = '#ccc';
        document.getElementById('password').style.borderColor = '#ccc';
        document.getElementById('passwordRepeat').style.borderColor = '#ccc';
        document.getElementById('name').style.borderColor = '#ccc';
        document.getElementById('surname').style.borderColor = '#ccc';
        document.getElementById('birth').style.borderColor = '#ccc';

        // Pobranie danych
        const login = document.getElementById('login').value.trim();
        const password = document.getElementById('password').value;
        const passwordRepeat = document.getElementById('passwordRepeat').value;
        const name = document.getElementById('name').value.trim();
        const surname = document.getElementById('surname').value.trim();
        const birth = document.getElementById('birth').value.trim();

        // Sprawdzenie danych
        if (!isValidString(login, 'login', 'nazwa użytkownika', 255)) {
            registerQueue = false;
            return;
        }

        if (!isValidString(password, 'password', 'hasło', 255, 8)) {
            registerQueue = false;
            return;
        }

        if (password !== passwordRepeat) {
            document.getElementById('password').style.borderColor = '#ff0000';
            document.getElementById('passwordRepeat').style.borderColor = '#ff0000';
            document.getElementById('loginBtn').innerHTML = 'Hasła się nie zgadzają';
            document.getElementById('loginBtn').style.background = '#ff0000';
            registerQueue = false;
            return;
        }

        if (!isValidString(name, 'name', 'imię', 32)) {
            registerQueue = false;
            return;
        }

        if (!isValidString(surname, 'surname', 'nazwisko', 64)) {
            registerQueue = false;
            return;
        }

        if (birth.length < 1) {
            document.getElementById('birth').style.borderColor = '#ff0000';
            document.getElementById('loginBtn').innerHTML = 'Data urodzenia nie jest poprawna';
            document.getElementById('loginBtn').style.background = '#ff0000';
            registerQueue = false;
            return;
        }

        // Stworzenie obiektu z danymi do rejestracji
        const postData = JSON.stringify({
            username:login,
            password,
            name,
            surname,
            birth
        });
        
        
        // Tutaj połączenie z backend żeby sprawdzić czy wszystko ok (przykładowe)
        const http = new XMLHttpRequest();
        
        http.onreadystatechange = (e) => {
        	
            if (http.readyState === 4 && http.status === 200) {
            	
            	if (http.responseText === 'true') {
            		document.getElementById('loginBtn').innerHTML = 'Zarejestrowany';
            		document.getElementById('loginBtn').style.background = '#25c425';
            		setTimeout(() => {
						window.location.href='/login';
					}, 1000);
            		
                } else {
                    document.getElementById('loginBtn').innerHTML = 'Zła nazwa użytkownika lub hasło';
                    document.getElementById('loginBtn').style.background = '#ff0000';
                    loginQueue = false;
                }
  
            }
        }

        http.open('POST', '/createuser');

        http.setRequestHeader("Content-Type", "application/json");
        http.send(postData);
    }
}

function isValidString(value, inputId, valueName, maxCharCount, minCharCount = 1) {
    if (value.length < minCharCount) { // Brak danych
        document.getElementById(inputId).style.borderColor = '#ff0000';
        if (minCharCount === 1)
            document.getElementById('loginBtn').innerHTML = `Pole ${valueName} jest wymagane`;
        else
            document.getElementById('loginBtn').innerHTML = `Pole ${valueName} powinno zawierać minimalnie ${minCharCount} znaków`;
        document.getElementById('loginBtn').style.background = '#ff0000';
        return false;
    }

    if (value.length > maxCharCount) { // Wartość za długa
        document.getElementById(inputId).style.borderColor = '#ff0000';
        document.getElementById('loginBtn').innerHTML = `Pole ${valueName} jest za długie`;
        document.getElementById('loginBtn').style.background = '#ff0000';
        return false;
    }

    return true;
}

// Przypisanie akcji do przycisku rejestracji
document.getElementById('loginBtn').addEventListener('click', () => {
    // Sprawdzenie rejestracji
    checkRegister();
});
