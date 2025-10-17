import json, pyodbc

def lambda_handler(event, context):
    try:
        body = json.loads(event['body'])

        id_cliente = body['id_cliente']
        total = body['total']

        # Parámetros de conexión
        driver = '{ODBC Driver 17 for SQL Server}'
        server = 'database-1.clu6gy7vcjed.us-east-1.rds.amazonaws.com'
        database = 'XDigital'
        username = 'admin'
        password = 'moviles2025.'

        with pyodbc.connect(f"Driver={driver};Server={server};Database={database};UID={username};PWD={password};") as conn:
            with conn.cursor() as cursor:
                cursor.execute("""
                    DECLARE @nuevo_id INT;
                    EXEC sp_CrearPedido ?, ?, @nuevo_id OUTPUT;
                    SELECT @nuevo_id;
                """, (id_cliente, total))

                row = cursor.fetchone()
                conn.commit()

                return {
                    'statusCode': 201,
                    'body': json.dumps({
                        'Mensaje': 'Pedido creado correctamente',
                        'Nuevo_Id': row[0]
                    })
                }

    except Exception as e:
        return {'Error': str(e)}
