import {ReactComponent as Logo} from "assets/svgs/logo.svg";

function starterPage(){
    return(
        <div style={{
            display: "block",
            textAlign: "center",
            position: "relative",
        }}>

            <div style={{
                marginTop: "17%"
            }}>
                <Logo></Logo>
            </div>

            <div style={{

            }}>
                <div style={{
                    marginTop: "168px",
                    color: "#000",
                    textAlign: "center",
                    fontFamily: "Roboto",
                    fontSize: "24px",
                    fontStyle: "bold",
                    fontWeight: "700",
                    lineHeight: "150%", /* 150% */
                    letterSpacing: "-0.32px",
                }}>
                    Discover the World’s
                </div>

                <div style={{
                    color: "#47B385",
                    textAlign: "center",
                    fontFamily: "Roboto",
                    fontSize: "24px",
                    fontStyle: "normal",
                    fontWeight: "700",
                    lineHeight: "150%", /* 150% */
                    letterSpacing: "-0.32px",
                }}>
                    best online shop!
                </div>
            </div>

            <div style={{
                marginTop: "220px",
                display: "flex",
                bottom: "0px",
                position: "60px sticky",
                width: "100%",
                justifyContent: "center",
                alignItems: "center",
            }}>
                <button style={{
                    width: "320px",
                    padding: "16px 10px",
                    borderRadius: "35px",
                    background: "#47B385",
                    borderColor: "transparent",

                    color: "#FFF",
                    textAlign: "center",
                    fontFamily: "Roboto",
                    fontSize: "14px",
                    fontStyle: "normal",
                    fontWeight: "500",
                    lineHeight: "21px", /* 131.25% */
                    letterSpacing: "-0.32px",
                }} onClick={() => console.log("clicked")}>Get started now</button>
            </div>
        </div>
    )
}

export default starterPage