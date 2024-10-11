function formatDate(dateString) {
    const date = new Date(dateString);
    const day = String(date.getDate()).padStart(2, '0'); // get Day and add leading zero
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Get month (0-11) and add 1
    const year = date.getFullYear();

    return `${day}.${month}.${year}`; // Formating date as dd.MM.yyyy
}

function formatNumber(num, decimalPlaces) {
    if (num === null || num === undefined || isNaN(num)) {
        return '';
    }

    const roundedNum = Number(num).toFixed(decimalPlaces);

    // get fract and intefer part
    const [integerPart, fractionalPart] = roundedNum.split('.');

    // format integer part with group digit
    const formattedInteger = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, ' ');

    // if fract part is empty, or equal 0, then return only integer part
    if (!fractionalPart || parseFloat(fractionalPart) === 0) {
        return formattedInteger;
    }

    // remove left zeros in fract part
    const trimmedFractionalPart = fractionalPart.replace(/0+$/, '');

    return `${formattedInteger},${trimmedFractionalPart}`;
}

function formatCurrency(amount) {
    return amount.toLocaleString('ru-RU', {
        style: 'currency',
        currency: 'RUB',
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    });
}