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

    // round the decimal fraction to the specified precision
    const roundedNum = Number(num).toFixed(decimalPlaces);
    // We divide the whole and fractional parts
    const [integerPart, fractionalPart] = roundedNum.split('.');
    // We format the whole part with division by digits
    const formattedInteger = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, ' ');
    // If the fractional part is empty, we return only the whole part
    if (!fractionalPart || parseFloat(fractionalPart) === 0) {
        return formattedInteger;
    }

    return `${formattedInteger},${fractionalPart}`;
}