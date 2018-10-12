# Bank-Application With TDD
#Requerimeintos: 
Esta aplicacion es un ejemplo pequeño de una aplicacion bancaria que tiene los siguientes requerimentos:

- <b>MODULO DE CUENTAS:</b> Se debe crear un modulo que permita la administracion de cuentas bancarias bajo las siguientes caracteristicas:

    * <b>NUMERO DE CUENTA:</b> Las cuentas bancarias deben tener un numero unico auocreado por la aplicacion bajo el siguiente formato:
        {CM}-{CD}-{CORRELATIVO}
            <br>Donde: 
            <br>CM: Codigo de moneda <b>201</b> para <b>BOLIVIANOS</b> y <b>202</b> para <b>DOLARES</b>.
            <br>CD: Codigo de departamento.
            <br>CORRELATIVO: Correlativo de 6 digitos Ej.: 000001.
    * <b>DEPARTAMENTO:</b> Valores validos son: LA PAZ, ORURO, POTOSI, PANDO, SANTA CRUZ, 
    BENI, COCHABAMBA, TARIJA, CHUQUISACA.                 
    * <b>NOMBRE DEL TITULAR:</b> El titular de la cuenta es el primer nombre del cliente seguido de su apellido paterno (ANGEL CHAMBI).
    
    * <b>MONEDA:</b> Una cadena que tiene unicamente dos opciones BOLIVIANOS Y DOLARES (en mayusculas).
    * <b>SALDO: </b> Un numero decimal de maximo 2 decimales y 10 enteros que expresa el saldo en la cuenta bancaria siempre debe ser mayor o igual a 0.
    
- <b>METODOS DEL API DE CUENTAS:</b> La administracion de las cuentas bancarias debe contar con API que se pueda realizar los siguientes metodos: 
    * <b>Almacenar cuentas,</b> Se puede almacenar con 0 de saldo o con un saldo inicial que debe estar registrado como un movimiento de ABONO. 
    * <b>Modificacion de cuentas,</b> la modificacion de cuentas se podra realizar unicamente de aquellas cuentas que no tengan movimientos bancarios. 
    * <b>Eliminacion de cuentas,</b> Al eliminar la cuenta no se eliminan fisicamente sino que se debe realizar un borrado logico.
    * <b>Listado de cuentas,</b> El listado de cuentas debe contener todos los campos (id, Numero de cuenta, Departamento, Nombre del titular, Moneda, Saldo).
    * <b>Listado Simple, </b> El listado de cuentas simple se hara para la utilizacion en combo box con los siguientes campos (id, numero de cuenta).
    
- <b>MODULO DE MOVIMIENTOS</b>: El modulo de movimientos tiene como tarea poder realizar un control de los debitos y abonos de las cuentas bancarias y para esto se requiere los siguientes campos:
    * <b>MONEDA:</b> BOLIVIANOS, DOLARES.
    * <b>CUENTA:</b> Cuenta de la cual se realizo el debito.
    * <b>TIPO DE MOVIMIENTO:</b> Unicamente se puede dos tipos de movimientos DEBITO y ABONO (siempre en mayusculas).
    * <b>MONTO: </b> el monto debe ser un numero decimal (positivo o negativo nunca 0) de 10 digitos enteros y 2 decimales.
    
- <b>METODOS DEL API DE MOVIMIENTOS:
    * <b>CREACION DE MOVIMIENTO,</b> El movimiento de una cuenta bancaria debe insertar un nuevo registro del movimiento, si el movimiento es un ABONO se debe verificar que el monto del movimiento sea positivo y de ser un DEBITO el monto del movimiento debe ser negativo. Se debe tener en cuenta los tipos de cambios (Nota: tipos de cambio deben ser 6.85 compra y 67.97 venta).
    <br>
    <table>
        <tr>
            <td>TIPO DE MONEDA CTA.</td>
            <td>TIPO DE MOVIMIENTO</td>
            <td>MONEDA DE MOVIMIENTO</td>
            <td>DEBITO O ABONO EN LA CUENTA</td>
        </tr>
        <tr>
            <td>bolivianos</td>
            <td>debito</td>
            <td>bolivianos</td>
            <td>Redondeo(Monto)</td>
        </tr>
        <tr>
            <td>bolivianos</td>
            <td>debito</td>
            <td>dolares</td>
            <td>Redondeo(Monto*6.97)</td>
        </tr>
        <tr>
            <td>dolares</td>
            <td>debito</td>
            <td>bolivianos</td>
            <td>Redondeo(Monto/6.94)</td>
        </tr>
        <tr>
            <td>dolares</td>
            <td>debito</td>
            <td>dolares</td>
            <td>Redondeo(Monto)</td>
        </tr>
    </table>
    <br>
    <table>
            <tr>
                <td>TIPO DE MONEDA CTA.</td>
                <td>TIPO DE MOVIMIENTO</td>
                <td>MONEDA DE MOVIMIENTO</td>
                <td>DEBITO O ABONO EN LA CUENTA</td>
            </tr>
            <tr>
                <td>bolivianos</td>
                <td>abono</td>
                <td>bolivianos</td>
                <td>Redondeo(Monto)</td>
            </tr>
            <tr>
                <td>bolivianos</td>
                <td>abono</td>
                <td>dolares</td>
                <td>Redondeo(Monto*6.94)</td>
            </tr>
            <tr>
                <td>dolares</td>
                <td>abono</td>
                <td>bolivianos</td>
                <td>Redondeo(Monto/6.97)</td>
            </tr>
            <tr>
                <td>dolares</td>
                <td>abono</td>
                <td>dolares</td>
                <td>Redondeo(Monto)</td>
            </tr>
        </table>
   
    * TRANSFERENCIA ENTRE CUENTAS (TERCEROS): Una transferencia entre cuentas bancarias debe realizar dos movimientos uno de debito en al cuenta origen y otro de abono en la cuenta destino. bajo los criterios descritos en el metodo de creacion de movimiento
