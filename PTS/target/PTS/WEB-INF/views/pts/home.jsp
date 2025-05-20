<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Home Page</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            position: relative;
            min-height: 100vh;
            font-family: Arial, sans-serif;
        }

        body::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 80%;
            height: 100%;
            background-image: url('./img/banner/new_dashboard_cropped.png');
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            clip-path: polygon(0 0, 100% 0, 80% 100%, 0 100%);
            z-index: -1;
        }

        .topic {
            position: absolute;
            top: 20px;
            right: 30px;
            color: #333;
            font-size: 24px;
            font-weight: bold;
        }

        .card-container {
            position: absolute;
            right: 50px;
            top: 50%;
            transform: translateY(-20%);
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        .card {
            background: rgba(255, 255, 255, 0.9);
            padding: 20px;
            border-radius: 10px;
            width: 250px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            cursor: pointer;
            transition: transform 0.3s ease;
        }

        .card:hover {
            transform: scale(1.05);
        }

        .card h3 {
            margin: 0 0 10px 0;
            color: #333;
        }

        .card p {
            margin: 0;
            color: #666;
        }
    </style>
</head>
<body>
<div class="topic">Energy Marketing</div>



<div class="main-menu d-none d-lg-block" style="display: flex; justify-content: flex-start; align-items: center; margin: 0px;">
    <nav style="width: 10%; text-align: left;">
        <ul id="navigation" style="list-style: none; padding: 0; margin: 20px; display: flex; flex-wrap: wrap; gap: 10px;">
            <!--  <li style="text-decoration: none; font-weight: bold; flex: 1 1 45%; text-align: center;">
                  <a href="WelcomePTS" style="text-decoration: none; color: black; font-weight: bold; display: flex; align-items: center; justify-content: center;">
                      <img src="resources/icons/home.svg" alt="Home Icon" style="width: 20px; height: 20px; margin-right: 5px;">

                  </a>
              </li>  -->
            <li style="text-decoration: none; font-weight: bold; flex: 1 1 auto; text-align: left; display: flex; align-items: center; gap: 5px; width: auto;">
                <a href="WelcomePTS" style="text-decoration: none; color: black; font-weight: bold; display: flex; align-items: center;">
                    <img src="./icons/logout.svg" alt="Logout Icon" style="width: 40px; height: 40px;">
                </a>
            </li>
        </ul>
    </nav>
</div>

<div class="card-container" style="margin-right: 250px;">
    <div style="display: flex; flex-direction: column; gap: 20px;">


        <a href="licenseeBillingHome" style="text-decoration: none;">
        <div class="card" style="background-color:rgb(144, 207, 246); width: 300px; height: 80px; display: flex; align-items: center;">
            <img src="resources/icons/electricity.svg" alt="Icon" style="width: 50px; height: 50px; margin-right: 10px;">
            <div>
                <h3>Distribution Licensee Billing</h3>
                <p></p>
            </div>
        </div>
        </a>


        <a href="consumerBillingHome" style="text-decoration: none;">
        <div class="card" style="background-color:rgb(144, 207, 246); width: 300px; height: 80px; display: flex; align-items: center;">
            <img src="resources/icons/transmission-tower.svg" alt="Icon" style="width: 50px; height: 50px; margin-right: 10px;">
            <div>
                <h3>Transmission Consumer Billing</h3>
                <p></p>
            </div>
        </div>
        </a>


    </div>
</div>
</body>
</html>